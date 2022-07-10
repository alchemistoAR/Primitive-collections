package pkg.collections;

import java.util.Arrays;
import java.util.Objects;

public class PrimitiveIntCollection {
    private static final int SHIFT = 10;
    private static final int CHUNK_SIZE = 1 << SHIFT; // 1024

    private int[][] array;

    private int[] lastChunk;
    private int lastChunkPosition;

    private int size;

    public PrimitiveIntCollection() {
        array = new int[1][CHUNK_SIZE];
        lastChunk = array[0];
        lastChunkPosition = 0;
    }

    public int getSize() {
        return size;
    }

    public int get(final int index) {
        RangeCheck.check(index, size);
        int chunkIndex = index >> SHIFT;
        int subIndex = index - (chunkIndex << SHIFT);
        return array[chunkIndex][subIndex];
    }

    public void add(final int value) {
        if (lastChunkPosition == CHUNK_SIZE) {
            int lastIndex = array.length;
            array = Arrays.copyOf(array, array.length + 1);
            array[lastIndex] = new int[CHUNK_SIZE];
            lastChunk = array[lastIndex];
            lastChunkPosition = 0;
        }
        lastChunk[lastChunkPosition] = value;
        lastChunkPosition = lastChunkPosition + 1;
        size = size + 1;
    }

    public void set(final int index, final int value) {
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

        array = new int[1][CHUNK_SIZE];
        lastChunk = array[0];
    }

    public double sum() {
        if (size == 0) {
            throw new IllegalStateException("Can't calculate, collection is empty");
        }
        double sum = 0;
        int i = 0;
        for (int[] chunk : array) {
            for (int v : chunk) {
                sum = sum + v;
                i++;
                if (i == size) {
                    break;
                }
            }
            if (i == size) {
                break;
            }
        }
        return sum;
    }

    public double average() {
        return sum() / size;
    }

    public int max() {
        if (size == 0) {
            throw new IllegalStateException("Can't calculate, collection is empty");
        }
        int max = array[0][0];
        int i = 0;
        for (int[] chunk : array) {
            for (int v : chunk) {
                if (v > max) {
                    max = v;
                }
                i++;
                if (i == size) {
                    break;
                }
            }
            if (i == size) {
                break;
            }
        }
        return max;
    }

    public int min() {
        if (size == 0) {
            throw new IllegalStateException("Can't calculate, collection is empty");
        }
        int min = array[0][0];
        int i = 0;
        for (int[] chunk : array) {
            for (int v : chunk) {
                if (v < min) {
                    min = v;
                }
                i++;
                if (i == size) {
                    break;
                }
            }
            if (i == size) {
                break;
            }
        }
        return min;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(array);
    }

    @Override
    public boolean equals(final Object obj) {
        Objects.requireNonNull(obj);
        if (obj instanceof PrimitiveIntCollection) {
            return equals((PrimitiveIntCollection) obj);
        } else {
            throw new IllegalArgumentException("Wrong argument type, required PrimitiveIntCollection class");
        }
    }

    public boolean equals(final PrimitiveIntCollection second) {
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
