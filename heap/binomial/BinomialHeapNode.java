package heap.binomial;

import heap.EmptyHeapException;
import heap.wrapper.ValueWrapper;

public class BinomialHeapNode<T extends Comparable<T>> {

    // Bevat een variabele van type T en biedt ondersteuning voor -oneindig
    ValueWrapper<T> value;

    // Ouder van de node
    BinomialHeapNode<T> parent;

    // Graad van de node
    int degree;

    // Linkerkind van de node
    BinomialHeapNode<T> lchild;

    // Referentie naar het volgende kind van de ouder van de node
    BinomialHeapNode<T> sibling;

    // Referentie naar het overeenkomstig binomialheapelement
    BinomialHeapElement<T> el;

    // Referentie naar de heap
    BinomialHeap<T> heap;

    BinomialHeapNode(T value, BinomialHeap<T> heap) {
        this.value = new ValueWrapper<>(value);
        this.parent = null;
        this.degree = 0;
        this.lchild = null;
        this.sibling = null;
        this.el = new BinomialHeapElement<>(this);
        this.heap = heap;
    }


    /**
     * @return
     * Geeft de waarde terug van de node
     */
    public T value() {
        // Aangezien de waarde in een valuewrapper zit moet de waarde van deze wrapper
        // opgevraagd worden
        return value.value;
    }

    /**
     * Verwijderd deze node uit de heap
     */
    void remove() {
        // Waarde updaten naar min oneindig
        percolateUp(ValueWrapper.minInfinity());
        try {
            // Minimale element uit de heap verwijderen
            heap.removeMin();
        } catch (EmptyHeapException ex) {
            System.out.println("shouldn't happen");
        }
    }


    /**
     * Zorgt ervoor dat de node naar boven percolleert voor zolang dat nodig is
     * @param val
     * De nieuwe waarde voor de node
     */
    void percolateUp(ValueWrapper<T> val) {

        BinomialHeapElement<T> tmpel = el;

        BinomialHeapNode<T> current = this;
        // Zolang waarde kleiner is dan zijn ouder
        while ((current.parent != null) && (val.compareTo(current.parent.value) < 0)) {
            // Waarde aanpassen
            current.value = current.parent.value;

            // BinomialHeapElement dat verwijst naar deze node aanpassen
            current.el = current.parent.el;
            current.el.node = current;

            // Huidige node wordt de ouder
            current = current.parent;
        }

        // Waarde op juiste plaats invoegen
        current.value = val;

        // BinomialHeapElement dat verwijst naar deze node aanpassen
        current.el = tmpel;
        current.el.node = current;


    }

}

