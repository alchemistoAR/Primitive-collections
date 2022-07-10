package pkg.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PrimitiveByteCollectionTest {

    private final int iterations = 10_000;

    @Test
    void getSize() {
        final PrimitiveByteCollection collection = new PrimitiveByteCollection();
        for (int i = 0; i < iterations; i++) {
            Assertions.assertEquals(i, collection.getSize());
            collection.add((byte) i);
            Assertions.assertEquals(i + 1, collection.getSize());
        }
    }

    @Test
    void addAndGet() {
        final PrimitiveByteCollection collection = new PrimitiveByteCollection();
        final byte[] expected = new byte[iterations];
        for (int i = 0; i < iterations; i++) {
            final int finalI = i;
            expected[i] = (byte) i;
            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(finalI));
            collection.add((byte) i);
            Assertions.assertEquals((byte) i, collection.get(i));
        }
        final byte[] actual = new byte[iterations];
        for (int i = 0; i < collection.getSize(); i++) {
            actual[i] = collection.get(i);
        }
        Assertions.assertArrayEquals(expected, actual);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(Integer.MIN_VALUE));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-iterations));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(iterations));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(iterations + 1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(Integer.MAX_VALUE));
    }

    @Test
    void set() {
        final PrimitiveByteCollection collection = new PrimitiveByteCollection();
        final byte[] expected = new byte[iterations];
        for (int i = 0; i < iterations; i++) {
            expected[i] = (byte) (i + 2);
            collection.add((byte) i);
            Assertions.assertEquals((byte) i, collection.get(i));
            collection.set(i, (byte) (i + 1));
            Assertions.assertEquals((byte) (i + 1), collection.get(i));
        }
        for (int i = 0; i < collection.getSize(); i++) {
            collection.set(i, (byte) (collection.get(i) + 1));
        }
        final byte[] actual = new byte[iterations];
        for (int i = 0; i < iterations; i++) {
            actual[i] = collection.get(i);
        }
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void clean() {
        final PrimitiveByteCollection collection = new PrimitiveByteCollection();
        for (int i = 0; i < iterations; i++) {
            collection.add((byte) i);
        }
        Assertions.assertEquals(iterations, collection.getSize());
        collection.clean();
        Assertions.assertEquals(0, collection.getSize());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
    }

    @Test
    void testHashCode() {
        final PrimitiveByteCollection collection1 = new PrimitiveByteCollection();
        final PrimitiveByteCollection collection2 = new PrimitiveByteCollection();
        final PrimitiveByteCollection collection3 = new PrimitiveByteCollection();
        final PrimitiveByteCollection collection4 = new PrimitiveByteCollection();
        for (int i = 0; i < iterations; i++) {
            collection1.add((byte) i);
            collection2.add((byte) i);
            if (i % 2 == 0) {
                collection3.add((byte) i);
            }
        }

        final int hashcode1 = collection1.hashCode();
        final int hashcode2 = collection2.hashCode();
        final int hashcode3 = collection3.hashCode();
        final int hashcode4 = collection4.hashCode();

        Assertions.assertEquals(hashcode1, collection1.hashCode());
        Assertions.assertEquals(hashcode1, hashcode2);
        Assertions.assertNotEquals(hashcode1, hashcode3);
        Assertions.assertNotEquals(hashcode1, hashcode4);
        Assertions.assertNotEquals(hashcode3, hashcode4);
    }

    @Test
    void testEquals() {
        final PrimitiveByteCollection collection1 = new PrimitiveByteCollection();
        final PrimitiveByteCollection collection2 = new PrimitiveByteCollection();
        final PrimitiveByteCollection collection3 = new PrimitiveByteCollection();
        final PrimitiveByteCollection collection4 = new PrimitiveByteCollection();
        for (int i = 0; i < iterations; i++) {
            collection1.add((byte) i);
            collection2.add((byte) i);
            if (i % 2 == 0) {
                collection3.add((byte) i);
            }
        }

        Assertions.assertEquals(collection1, collection1);
        Assertions.assertEquals(collection1, collection2);

        Assertions.assertNotEquals(collection1, collection3);
        Assertions.assertNotEquals(collection1, collection4);
        Assertions.assertNotEquals(collection3, collection4);

        collection2.set(collection2.getSize() - 1, (byte) -1);
        Assertions.assertNotEquals(collection1, collection2);

        collection2.set(collection2.getSize() - 1, (byte) (iterations - 1));
        Assertions.assertEquals(collection1, collection2);

        collection2.set(0, (byte) -1);
        Assertions.assertNotEquals(collection1, collection2);

        Assertions.assertThrows(NullPointerException.class, () -> collection1.equals(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> collection1.equals("123"));
    }

    @Test
    void testClone() {
        final PrimitiveByteCollection collection = new PrimitiveByteCollection();
        Assertions.assertThrows(CloneNotSupportedException.class, () -> collection.clone());
    }

    @Test
    void testToString() {
        final PrimitiveByteCollection collection = new PrimitiveByteCollection();
        Assertions.assertNotNull(collection.toString());
        for (int i = 0; i < iterations; i++) {
            collection.add((byte) i);
        }
        Assertions.assertNotNull(collection.toString());
    }
}
