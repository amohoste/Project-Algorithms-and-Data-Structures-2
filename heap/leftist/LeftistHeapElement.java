package heap.leftist;

import heap.Element;

public class LeftistHeapElement <T extends Comparable<T>> implements Element<T> {

    LeftistHeapElement<T> left;
    LeftistHeapElement<T> right;
    LeftistHeapElement<T> parent;
    private LeftistHeap<T> heap;
    T value;
    int npl;

    LeftistHeapElement(T value, LeftistHeap<T> heap) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.npl = 0;
        this.heap = heap;
    }

    /**
     * @return
     * De waarde van het element
     */
    @Override
    public T value() {
        return value;
    }

    /**
     * Verwijderd het element uit de heap
     */
    @Override
    public void remove() {
        heap.remove(this);
    }

    /**
     * Past de waarde van het element aan
     * @param value
     * De nieuwe waarde van het element
     */
    @Override
    public void update(T value) {
        // Element verwijderen uit de heap
        heap.remove(this);

        // Nieuw element in heap plaatsen met nieuwe waarde
        LeftistHeapElement<T> nieuw = heap.insert(value);

        // Velden nieuw element overkopiëren en element vervangen door dit element
        this.value = nieuw.value;
        this.left = nieuw.left;
        if (this.left != null) {
            this.left.parent = this;
        }
        this.right = nieuw.right;
        if (this.right != null) {
            this.right.parent = this;
        }

        this.parent = nieuw.parent;
        this.npl = nieuw.npl;

        if (parent != null) {
            if (parent.right != null && parent.right.equals(nieuw)) {
                parent.right = this;
            } else {
                parent.left = this;
            }
        } else {
            heap.root = this;
        }
    }
}
