package weather.util;

import java.io.Serializable;

public class Pair<T1, T2> implements Serializable {
    private T1 value1;
    private T2 value2;

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<L, R>(left, right);
    }

    public Pair(T1 value1, T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T1 getLeft() {
        return value1;
    }

    public T2 getRight() {
        return value2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        return !(value1 != null ? !value1.equals(pair.value1) : pair.value1 != null) &&
                !(value2 != null ? !value2.equals(pair.value2) : pair.value2 != null);
    }

    @Override
    public int hashCode() {
        int result = value1 != null ? value1.hashCode() : 0;
        result = 31 * result + (value2 != null ? value2.hashCode() : 0);
        return result;
    }
}
