package ru.otus.mygson;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TestObject {
    private final int value1as;
    private final String value2;
    private final int value3;
    private final Double value4;
    private final int[] value5;
    private final List<Integer> value6;
    private final Set<String> value7;

    TestObject(int value1as, String value2, int value3, Double value4, int[] value5, List<Integer> value6, Set<String> value7) {
        this.value1as = value1as;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
        this.value6 = value6;
        this.value7 = value7;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "value1as=" + value1as +
                ", value2='" + value2 + '\'' +
                ", value3=" + value3 +
                ", value4=" + value4 +
                ", value5=" + Arrays.toString(value5) +
                ", value6=" + value6 +
                ", value7=" + value7 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestObject that = (TestObject) o;
        return value1as == that.value1as &&
                value3 == that.value3 &&
                Objects.equals(value2, that.value2) &&
                Objects.equals(value4, that.value4) &&
                Arrays.equals(value5, that.value5) &&
                Objects.equals(value6, that.value6) &&
                Objects.equals(value7, that.value7);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(value1as, value2, value3, value4, value6, value7);
        result = 31 * result + Arrays.hashCode(value5);
        return result;
    }

}
