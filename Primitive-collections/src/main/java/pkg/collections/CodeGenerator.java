package pkg.collections;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

class CodeGenerator {
    public static void main(final String[] args) throws IOException {
        final String sourceDirectory = System.getenv("SourceCodeDirectory");
        final String testDirectory = System.getenv("TestCodeDirectory");

        Objects.requireNonNull(sourceDirectory);
        Objects.requireNonNull(testDirectory);

        final String[] classNames = new String[]{"Float", "Long", "Int", "Short", "Byte"};
        final String[] typeNames = new String[]{"float", "long", "int", "short", "byte"};

        if (classNames.length != typeNames.length) {
            System.out.println("Size not equal");
            System.exit(1);
        }

        for (String directory : new String[]{sourceDirectory, testDirectory}) {
            final Path path = Paths.get(directory);

            List<String> lines = null;
            if (directory.equals(sourceDirectory)) {
                lines = Files.readAllLines(path.resolve("PrimitiveDoubleCollection.java"));
            } else if (directory.equals(testDirectory)) {
                lines = Files.readAllLines(path.resolve("PrimitiveDoubleCollectionTest.java"));
            }

            for (int i = 0; i < classNames.length; i++) {
                final String className = classNames[i];
                final String typeName = typeNames[i];

                File file = null;
                if (directory.equals(sourceDirectory)) {
                    file = path.resolve("Primitive" + className + "Collection.java").toFile();
                } else if (directory.equals(testDirectory)) {
                    file = path.resolve("Primitive" + className + "CollectionTest.java").toFile();
                }

                if (file.exists()) {
                    file.delete();
                }

                boolean replace = true;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (final String line : lines) {
                        if (line.contains("// Start replace")) {
                            replace = true;
                            continue;
                        }
                        if (line.contains("// Stop replace")) {
                            replace = false;
                            continue;
                        }
                        String newLine = line;
                        if (replace) {
                            newLine = line.replaceAll("Double", className).replaceAll("double", typeName);
                        }
                        writer.write(newLine);
                        writer.newLine();
                    }

                }
            }
        }
    }
}
