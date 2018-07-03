package heap.pairing;

import heap.Element;
import heap.EmptyHeapException;
import heap.binomial.BinomialHeap;
import heap.wrapper.ValueWrapper;

public class PairingHeapElement<T extends Comparable<T>> implements Element<T> {

    // Als het element het meest linkse is op een niveau verwijst previous naar zijn ouder
    PairingHeapElement<T> previous;
    PairingHeapElement<T> next;
    PairingHeapElement<T> child;
    private PairingHeap<T> heap;
    T value;

    PairingHeapElement(T value, PairingHeap<T> heap) {
        this.value = value;
        this.previous = null;
        this.next = null;
        this.child = null;
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
        try {
            // Deelboom met dit element als root verwijderen uit boom
            if (previous != null) {

                if (previous.child != null && previous.child.equals(this)) {
                    // Als te verwijderen element een direct kind is
                    previous.child = next;
                } else {
                    // Als te verwijderen element een sibling is
                    previous.next = next;
                }
                if (next != null) {
                    next.previous = previous;
                }
                previous = null;
                next = null;

                // Verwijder wortel deelboom en schakel resutaat hiervan terug aan de hoop
                PairingHeapElement<T> subtree = heap.removeSubTree(this);
                this.child = null;

                heap.root = heap.merge(heap.root, subtree);

            } else {
                heap.removeMin();
            }

        } catch (EmptyHeapException ex) {
            System.out.println("error, shouldn't happen");
        }
    }

    /**
     * Past de waarde van het element aan
     * @param value
     * De nieuwe waarde van het element
     */
    @Override
    public void update(T value) {

        // Kijken of nieuwe waarde kleiner / groter is dan ouder waarde
        int verschil = value.compareTo(this.value);

        if (verschil < 0) {
            // Als nieuwe waarde kleiner is dan oude

            // Waarde aanpassen
            this.value = value;

            // Deelboom met dit element als wortel verwijderen uit de boom
            if (previous != null) {

                if (previous.child != null && previous.child.equals(this)) {
                    previous.child = next;
                } else {
                    previous.next = next;
                }
                if (next != null) {
                    next.previous = previous;
                }
                previous = null;
                next = null;
            }

            // Heap mergen met onze deelboom
            heap.root = heap.merge(heap.root, this);

        } else if (verschil > 0){
            // Als nieuwewaarde groter is dan oude

            // Element verwijderen uit de heap
            remove();

            // Nieuw element in heap plaatsen met nieuwe waarde
            PairingHeapElement<T> nieuw = heap.insert(value);

            // Velden nieuw element overkopiëren en element vervangen door dit element
            this.value = nieuw.value;
            this.child = nieuw.child;
            if (this.child != null) {
                this.child.previous = this;
            }


            this.next = nieuw.next;
            if (this.next != null) {
                this.next.previous = this;
            }

            this.previous = nieuw.previous;
            if (this.previous != null) {
                if (this.previous.child != null && this.previous.child.equals(nieuw)) {
                    // Als te verwijderen element een direct kind is
                    this.previous.child = this;
                } else {
                    // Als te verwijderen element een sibling is
                    this.previous.next = this;
                }
            }
        }

    }
}
