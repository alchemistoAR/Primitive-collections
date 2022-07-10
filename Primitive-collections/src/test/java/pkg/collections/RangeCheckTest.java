package pkg.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RangeCheckTest {

    @Test
    void check() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> RangeCheck.check(-1, -1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> RangeCheck.check(-1, 1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> RangeCheck.check(0, -1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> RangeCheck.check(0, 0));
        Assertions.assertDoesNotThrow(() -> RangeCheck.check(0, 1));
    }
}