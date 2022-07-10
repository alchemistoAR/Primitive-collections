package pkg.collections;

public class RangeCheck {
    public static void check(final int index, final int size) {
        if (index < 0 | size < 0 | index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Size = " + size);
        }
    }
}
