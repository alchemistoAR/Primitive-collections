package pkg.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

class PrimitiveIntCollectionTest {

    private final int iterations = 10_000;

    @Test
    void getSize() {
        final PrimitiveIntCollection collection = new PrimitiveIntCollection();
        for (int i = 0; i < iterations; i++) {
            Assertions.assertEquals(i, collection.getSize());
            collection.add((int) i);
            Assertions.assertEquals(i + 1, collection.getSize());
        }
    }

    @Test
    void addAndGet() {
        final PrimitiveIntCollection collection = new PrimitiveIntCollection();
        final int[] expected = new int[iterations];
        for (int i = 0; i < iterations; i++) {
            final int finalI = i;
            expected[i] = (int) i;
            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(finalI));
            collection.add((int) i);
            Assertions.assertEquals((int) i, collection.get(i));
        }
        final int[] actual = new int[iterations];
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
        final PrimitiveIntCollection collection = new PrimitiveIntCollection();
        final int[] expected = new int[iterations];
        for (int i = 0; i < iterations; i++) {
            expected[i] = (int) (i + 2);
            collection.add((int) i);
            Assertions.assertEquals((int) i, collection.get(i));
            collection.set(i, (int) (i + 1));
            Assertions.assertEquals((int) (i + 1), collection.get(i));
        }
        for (int i = 0; i < collection.getSize(); i++) {
            collection.set(i, (int) (collection.get(i) + 1));
        }
        final int[] actual = new int[iterations];
        for (int i = 0; i < iterations; i++) {
            actual[i] = collection.get(i);
        }
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void clean() {
        final PrimitiveIntCollection collection = new PrimitiveIntCollection();
        for (int i = 0; i < iterations; i++) {
            collection.add((int) i);
        }
        Assertions.assertEquals(iterations, collection.getSize());
        collection.clean();
        Assertions.assertEquals(0, collection.getSize());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
    }

    @Test
    void sum() {
        int sum = 0;
        final PrimitiveIntCollection collection = new PrimitiveIntCollection();
        Assertions.assertThrows(IllegalStateException.class, () -> collection.sum());
        for (int i = 0; i < 10; i++) {
            collection.add((int) i);
            sum = sum + i;
        }
        Assertions.assertEquals(sum, collection.sum());
    }

    @Test
    void average() {
        final PrimitiveIntCollection collection = new PrimitiveIntCollection();
        Assertions.assertThrows(IllegalStateException.class, () -> collection.average());
        for (int i = 0; i < 10; i++) {
            collection.add((int) i);
        }
        Assertions.assertEquals(4.5, collection.average());
    }

    @Test
    void max() {
        final Random random = new Random();
        final PrimitiveIntCollection collection = new PrimitiveIntCollection();
        Assertions.assertThrows(IllegalStateException.class, () -> collection.max());
        for (int i = 0; i < 10; i++) {
            collection.add((int) random.nextInt());
        }
        final int max = collection.max();
        for (int i = 0; i < 10; i++) {
            if (collection.get(i) > max) {
                Assertions.fail("Value = " + collection.get(i));
            }
        }
    }

    @Test
    void min() {
        final Random random = new Random();
        final PrimitiveIntCollection collection = new PrimitiveIntCollection();
        Assertions.assertThrows(IllegalStateException.class, () -> collection.min());
        for (int i = 0; i < 10; i++) {
            collection.add((int) random.nextInt());
        }
        final int min = collection.min();
        for (int i = 0; i < 10; i++) {
            if (collection.get(i) < min) {
                Assertions.fail("Value = " + collection.get(i));
            }
        }
    }

    @Test
    void testHashCode() {
        final PrimitiveIntCollection collection1 = new PrimitiveIntCollection();
        final PrimitiveIntCollection collection2 = new PrimitiveIntCollection();
        final PrimitiveIntCollection collection3 = new PrimitiveIntCollection();
        final PrimitiveIntCollection collection4 = new PrimitiveIntCollection();
        for (int i = 0; i < iterations; i++) {
            collection1.add((int) i);
            collection2.add((int) i);
            if (i % 2 == 0) {
                collection3.add((int) i);
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
        final PrimitiveIntCollection collection1 = new PrimitiveIntCollection();
        final PrimitiveIntCollection collection2 = new PrimitiveIntCollection();
        final PrimitiveIntCollection collection3 = new PrimitiveIntCollection();
        final PrimitiveIntCollection collection4 = new PrimitiveIntCollection();
        for (int i = 0; i < iterations; i++) {
            collection1.add((int) i);
            collection2.add((int) i);
            if (i % 2 == 0) {
                collection3.add((int) i);
            }
        }

        Assertions.assertEquals(collection1, collection1);
        Assertions.assertEquals(collection1, collection2);

        Assertions.assertNotEquals(collection1, collection3);
        Assertions.assertNotEquals(collection1, collection4);
        Assertions.assertNotEquals(collection3, collection4);

        collection2.set(collection2.getSize() - 1, (int) -1);
        Assertions.assertNotEquals(collection1, collection2);

        collection2.set(collection2.getSize() - 1, (int) (iterations - 1));
        Assertions.assertEquals(collection1, collection2);

        collection2.set(0, (int) -1);
        Assertions.assertNotEquals(collection1, collection2);

        Assertions.assertThrows(NullPointerException.class, () -> collection1.equals(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> collection1.equals("123"));
    }

    @Test
    void testClone() {
        final PrimitiveIntCollection collection = new PrimitiveIntCollection();
        Assertions.assertThrows(CloneNotSupportedException.class, () -> collection.clone());
    }

    @Test
    void testToString() {
        final PrimitiveIntCollection collection = new PrimitiveIntCollection();
        Assertions.assertNotNull(collection.toString());
        for (int i = 0; i < iterations; i++) {
            collection.add((int) i);
        }
        Assertions.assertNotNull(collection.toString());
    }
}
