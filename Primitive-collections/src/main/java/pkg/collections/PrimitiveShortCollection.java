package pkg.collections;

import java.util.Arrays;
import java.util.Objects;

public class PrimitiveShortCollection {
    private static final int SHIFT = 10;
    private static final int CHUNK_SIZE = 1 << SHIFT; // 1024

    private short[][] array;

    private short[] lastChunk;
    private int lastChunkPosition;

    private int size;

    public PrimitiveShortCollection() {
        array = new short[1][CHUNK_SIZE];
        lastChunk = array[0];
        lastChunkPosition = 0;
    }

    public int getSize() {
        return size;
    }

    public short get(final int index) {
        RangeCheck.check(index, size);
        int chunkIndex = index >> SHIFT;
        int subIndex = index - (chunkIndex << SHIFT);
        return array[chunkIndex][subIndex];
    }

    public void add(final short value) {
        if (lastChunkPosition == CHUNK_SIZE) {
            int lastIndex = array.length;
            array = Arrays.copyOf(array, array.length + 1);
            array[lastIndex] = new short[CHUNK_SIZE];
            lastChunk = array[lastIndex];
            lastChunkPosition = 0;
        }
        lastChunk[lastChunkPosition] = value;
        lastChunkPosition = lastChunkPosition + 1;
        size = size + 1;
    }

    public void set(final int index, final short value) {
        RangeCheck.check(index, size);
        int chunkIndex = index >> SHIFT;
        int subIndex = index - (chunkIndex << SHIFT);
        array[chunkIndex][subIndex] = value;
    }

    public void clean() {
        array = null;
        size = 0;
        lastChunk = null;
        lastChunkPosition = 0;

        array = new short[1][CHUNK_SIZE];
        lastChunk = array[0];
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(array);
    }

    @Override
    public boolean equals(final Object obj) {
        Objects.requireNonNull(obj);
        if (obj instanceof PrimitiveShortCollection) {
            return equals((PrimitiveShortCollection) obj);
        } else {
            throw new IllegalArgumentException("Wrong argument type, required PrimitiveShortCollection class");
        }
    }

    public boolean equals(final PrimitiveShortCollection second) {
        Objects.requireNonNull(second);
        if (this == second) {
            return true;
        }
        if (size == second.getSize()) {
            for (int i = 0; i < array.length; i++) {
                if (!Arrays.equals(array[i], second.array[i])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public String toString() {
        return Arrays.deepToString(array);
    }
}
