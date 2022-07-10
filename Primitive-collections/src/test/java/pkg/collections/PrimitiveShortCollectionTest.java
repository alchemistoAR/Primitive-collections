package pkg.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

class PrimitiveShortCollectionTest {

    private final int iterations = 10_000;

    @Test
    void getSize() {
        final PrimitiveShortCollection collection = new PrimitiveShortCollection();
        for (int i = 0; i < iterations; i++) {
            Assertions.assertEquals(i, collection.getSize());
            collection.add((short) i);
            Assertions.assertEquals(i + 1, collection.getSize());
        }
    }

    @Test
    void addAndGet() {
        final PrimitiveShortCollection collection = new PrimitiveShortCollection();
        final short[] expected = new short[iterations];
        for (int i = 0; i < iterations; i++) {
            final int finalI = i;
            expected[i] = (short) i;
            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(finalI));
            collection.add((short) i);
            Assertions.assertEquals((short) i, collection.get(i));
        }
        final short[] actual = new short[iterations];
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
        final PrimitiveShortCollection collection = new PrimitiveShortCollection();
        final short[] expected = new short[iterations];
        for (int i = 0; i < iterations; i++) {
            expected[i] = (short) (i + 2);
            collection.add((short) i);
            Assertions.assertEquals((short) i, collection.get(i));
            collection.set(i, (short) (i + 1));
            Assertions.assertEquals((short) (i + 1), collection.get(i));
        }
        for (int i = 0; i < collection.getSize(); i++) {
            collection.set(i, (short) (collection.get(i) + 1));
        }
        final short[] actual = new short[iterations];
        for (int i = 0; i < iterations; i++) {
            actual[i] = collection.get(i);
        }
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void clean() {
        final PrimitiveShortCollection collection = new PrimitiveShortCollection();
        for (int i = 0; i < iterations; i++) {
            collection.add((short) i);
        }
        Assertions.assertEquals(iterations, collection.getSize());
        collection.clean();
        Assertions.assertEquals(0, collection.getSize());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
    }

    @Test
    void sum() {
        int sum = 0;
        final PrimitiveShortCollection collection = new PrimitiveShortCollection();
        Assertions.assertThrows(IllegalStateException.class, () -> collection.sum());
        for (int i = 0; i < 10; i++) {
            collection.add((short) i);
            sum = sum + i;
        }
        Assertions.assertEquals(sum, collection.sum());
    }

    @Test
    void average() {
        final PrimitiveShortCollection collection = new PrimitiveShortCollection();
        Assertions.assertThrows(IllegalStateException.class, () -> collection.average());
        for (int i = 0; i < 10; i++) {
            collection.add((short) i);
        }
        Assertions.assertEquals(4.5, collection.average());
    }

    @Test
    void max() {
        final Random random = new Random();
        final PrimitiveShortCollection collection = new PrimitiveShortCollection();
        Assertions.assertThrows(IllegalStateException.class, () -> collection.max());
        for (int i = 0; i < 10; i++) {
            collection.add((short) random.nextInt());
        }
        final short max = collection.max();
        for (int i = 0; i < 10; i++) {
            if (collection.get(i) > max) {
                Assertions.fail("Value = " + collection.get(i));
            }
        }
    }

    @Test
    void min() {
        final Random random = new Random();
        final PrimitiveShortCollection collection = new PrimitiveShortCollection();
        Assertions.assertThrows(IllegalStateException.class, () -> collection.min());
        for (int i = 0; i < 10; i++) {
            collection.add((short) random.nextInt());
        }
        final short min = collection.min();
        for (int i = 0; i < 10; i++) {
            if (collection.get(i) < min) {
                Assertions.fail("Value = " + collection.get(i));
            }
        }
    }

    @Test
    void testHashCode() {
        final PrimitiveShortCollection collection1 = new PrimitiveShortCollection();
        final PrimitiveShortCollection collection2 = new PrimitiveShortCollection();
        final PrimitiveShortCollection collection3 = new PrimitiveShortCollection();
        final PrimitiveShortCollection collection4 = new PrimitiveShortCollection();
        for (int i = 0; i < iterations; i++) {
            collection1.add((short) i);
            collection2.add((short) i);
            if (i % 2 == 0) {
                collection3.add((short) i);
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
        final PrimitiveShortCollection collection1 = new PrimitiveShortCollection();
        final PrimitiveShortCollection collection2 = new PrimitiveShortCollection();
        final PrimitiveShortCollection collection3 = new PrimitiveShortCollection();
        final PrimitiveShortCollection collection4 = new PrimitiveShortCollection();
        for (int i = 0; i < iterations; i++) {
            collection1.add((short) i);
            collection2.add((short) i);
            if (i % 2 == 0) {
                collection3.add((short) i);
            }
        }

        Assertions.assertEquals(collection1, collection1);
        Assertions.assertEquals(collection1, collection2);

        Assertions.assertNotEquals(collection1, collection3);
        Assertions.assertNotEquals(collection1, collection4);
        Assertions.assertNotEquals(collection3, collection4);

        collection2.set(collection2.getSize() - 1, (short) -1);
        Assertions.assertNotEquals(collection1, collection2);

        collection2.set(collection2.getSize() - 1, (short) (iterations - 1));
        Assertions.assertEquals(collection1, collection2);

        collection2.set(0, (short) -1);
        Assertions.assertNotEquals(collection1, collection2);

        Assertions.assertThrows(NullPointerException.class, () -> collection1.equals(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> collection1.equals("123"));
    }

    @Test
    void testClone() {
        final PrimitiveShortCollection collection = new PrimitiveShortCollection();
        Assertions.assertThrows(CloneNotSupportedException.class, () -> collection.clone());
    }

    @Test
    void testToString() {
        final PrimitiveShortCollection collection = new PrimitiveShortCollection();
        Assertions.assertNotNull(collection.toString());
        for (int i = 0; i < iterations; i++) {
            collection.add((short) i);
        }
        Assertions.assertNotNull(collection.toString());
    }
}
