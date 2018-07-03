package heap.wrapper;


/**
 * Bevat een variabele van type T en biedt ondersteuning voor -oneindig
 */
public class ValueWrapper<T extends Comparable<T>> implements Comparable<ValueWrapper<T>> {

    private ValueWrapper() { }

    private static ValueWrapper minInfinity = new ValueWrapper();

    /**
     * @return
     * Minoneindig
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> ValueWrapper<T> minInfinity() {
        return (ValueWrapper<T>) minInfinity;
    }

    public T value;

    public ValueWrapper(T x) {
        value = x;
    }

    @Override
    public int compareTo(ValueWrapper<T> o) {
        if (this == o) {
            return 0;
        } else if (this == minInfinity) {
            return -1;
        }
        else if (o == minInfinity) {
            return 1;
        } else {
            return this.value.compareTo(o.value);
        }
    }
}
