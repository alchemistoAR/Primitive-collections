package pkg.collections;

import java.util.Arrays;
import java.util.Objects;

public class PrimitiveDoubleCollection {
    private static final int SHIFT = 10;
    private static final int CHUNK_SIZE = 1 << SHIFT; // 1024

    private double[][] array;

    private double[] lastChunk;
    private int lastChunkPosition;

    private int size;

    public PrimitiveDoubleCollection() {
        array = new double[1][CHUNK_SIZE];
        lastChunk = array[0];
        lastChunkPosition = 0;
    }

    public int getSize() {
        return size;
    }

    public double get(final int index) {
        RangeCheck.check(index, size);
        int chunkIndex = index >> SHIFT;
        int subIndex = index - (chunkIndex << SHIFT);
        return array[chunkIndex][subIndex];
    }

    public void add(final double value) {
        if (lastChunkPosition == CHUNK_SIZE) {
            int lastIndex = array.length;
            array = Arrays.copyOf(array, array.length + 1);
            array[lastIndex] = new double[CHUNK_SIZE];
            lastChunk = array[lastIndex];
            lastChunkPosition = 0;
        }
        lastChunk[lastChunkPosition] = value;
        lastChunkPosition = lastChunkPosition + 1;
        size = size + 1;
    }

    public void set(final int index, final double value) {
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

        array = new double[1][CHUNK_SIZE];
        lastChunk = array[0];
    }

    // Stop replace
    public double sum() {
        if (size == 0) {
            throw new IllegalStateException("Can't calculate, collection is empty");
        }
        double sum = 0;
        int i = 0;
        // Start replace
        for (double[] chunk : array) {
            for (double v : chunk) {
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

    // Stop replace
    public double average() {
        return sum() / size;
    }

    // Start replace
    public double max() {
        if (size == 0) {
            throw new IllegalStateException("Can't calculate, collection is empty");
        }
        double max = array[0][0];
        int i = 0;
        for (double[] chunk : array) {
            for (double v : chunk) {
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

    public double min() {
        if (size == 0) {
            throw new IllegalStateException("Can't calculate, collection is empty");
        }
        double min = array[0][0];
        int i = 0;
        for (double[] chunk : array) {
            for (double v : chunk) {
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
        if (obj instanceof PrimitiveDoubleCollection) {
            return equals((PrimitiveDoubleCollection) obj);
        } else {
            throw new IllegalArgumentException("Wrong argument type, required PrimitiveDoubleCollection class");
        }
    }

    public boolean equals(final PrimitiveDoubleCollection second) {
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
