package heap.binomial;

import heap.Element;
import heap.wrapper.ValueWrapper;

public class BinomialHeapElement<T extends Comparable<T>> implements Element<T> {

    // Node die overeenkomt met het binomialheapelement
    BinomialHeapNode<T> node;

    BinomialHeapElement(BinomialHeapNode<T> node) {
        this.node = node;
    }

    /**
     * @return
     * De waarde van het element
     */
    @Override
    public T value() {
        return node.value();
    }

    /**
     * Verwijderd het element uit de heap
     */
    @Override
    public void remove() {
        node.remove();
    }

    /**
     * Past de waarde van het element aan
     * @param val
     * De nieuwe waarde van het element
     */
    @Override
    public void update(T val) {
        int verschil = val.compareTo(node.value.value);
        // Als waarde kleiner is dan ouder waarde naar boven laten percolleren
        if (verschil < 0) {
            ValueWrapper<T> v = new ValueWrapper<>(val);
            node.percolateUp(v);
        } else if (verschil > 0){
            // Anders node verwijderen en dan opnieuw invoegen
            BinomialHeap<T> heap = node.heap;
            node.remove();
            this.node = heap.insert(val).node;
        }
    }
}
