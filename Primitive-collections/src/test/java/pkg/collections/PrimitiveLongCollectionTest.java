package pkg.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PrimitiveLongCollectionTest {

    private final int iterations = 10_000;

    @Test
    void getSize() {
        final PrimitiveLongCollection collection = new PrimitiveLongCollection();
        for (int i = 0; i < iterations; i++) {
            Assertions.assertEquals(i, collection.getSize());
            collection.add((long) i);
            Assertions.assertEquals(i + 1, collection.getSize());
        }
    }

    @Test
    void addAndGet() {
        final PrimitiveLongCollection collection = new PrimitiveLongCollection();
        final long[] expected = new long[iterations];
        for (int i = 0; i < iterations; i++) {
            final int finalI = i;
            expected[i] = (long) i;
            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(finalI));
            collection.add((long) i);
            Assertions.assertEquals((long) i, collection.get(i));
        }
        final long[] actual = new long[iterations];
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
        final PrimitiveLongCollection collection = new PrimitiveLongCollection();
        final long[] expected = new long[iterations];
        for (int i = 0; i < iterations; i++) {
            expected[i] = (long) (i + 2);
            collection.add((long) i);
            Assertions.assertEquals((long) i, collection.get(i));
            collection.set(i, (long) (i + 1));
            Assertions.assertEquals((long) (i + 1), collection.get(i));
        }
        for (int i = 0; i < collection.getSize(); i++) {
            collection.set(i, (long) (collection.get(i) + 1));
        }
        final long[] actual = new long[iterations];
        for (int i = 0; i < iterations; i++) {
            actual[i] = collection.get(i);
        }
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void clean() {
        final PrimitiveLongCollection collection = new PrimitiveLongCollection();
        for (int i = 0; i < iterations; i++) {
            collection.add((long) i);
        }
        Assertions.assertEquals(iterations, collection.getSize());
        collection.clean();
        Assertions.assertEquals(0, collection.getSize());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
    }

    @Test
    void testHashCode() {
        final PrimitiveLongCollection collection1 = new PrimitiveLongCollection();
        final PrimitiveLongCollection collection2 = new PrimitiveLongCollection();
        final PrimitiveLongCollection collection3 = new PrimitiveLongCollection();
        final PrimitiveLongCollection collection4 = new PrimitiveLongCollection();
        for (int i = 0; i < iterations; i++) {
            collection1.add((long) i);
            collection2.add((long) i);
            if (i % 2 == 0) {
                collection3.add((long) i);
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
        final PrimitiveLongCollection collection1 = new PrimitiveLongCollection();
        final PrimitiveLongCollection collection2 = new PrimitiveLongCollection();
        final PrimitiveLongCollection collection3 = new PrimitiveLongCollection();
        final PrimitiveLongCollection collection4 = new PrimitiveLongCollection();
        for (int i = 0; i < iterations; i++) {
            collection1.add((long) i);
            collection2.add((long) i);
            if (i % 2 == 0) {
                collection3.add((long) i);
            }
        }

        Assertions.assertEquals(collection1, collection1);
        Assertions.assertEquals(collection1, collection2);

        Assertions.assertNotEquals(collection1, collection3);
        Assertions.assertNotEquals(collection1, collection4);
        Assertions.assertNotEquals(collection3, collection4);

        collection2.set(collection2.getSize() - 1, (long) -1);
        Assertions.assertNotEquals(collection1, collection2);

        collection2.set(collection2.getSize() - 1, (long) (iterations - 1));
        Assertions.assertEquals(collection1, collection2);

        collection2.set(0, (long) -1);
        Assertions.assertNotEquals(collection1, collection2);

        Assertions.assertThrows(NullPointerException.class, () -> collection1.equals(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> collection1.equals("123"));
    }

    @Test
    void testClone() {
        final PrimitiveLongCollection collection = new PrimitiveLongCollection();
        Assertions.assertThrows(CloneNotSupportedException.class, () -> collection.clone());
    }

    @Test
    void testToString() {
        final PrimitiveLongCollection collection = new PrimitiveLongCollection();
        Assertions.assertNotNull(collection.toString());
        for (int i = 0; i < iterations; i++) {
            collection.add((long) i);
        }
        Assertions.assertNotNull(collection.toString());
    }
}
