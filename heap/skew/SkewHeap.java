package heap.skew;

import heap.EmptyHeapException;
import heap.Heap;
import heap.leftist.LeftistHeapElement;

public class SkewHeap<T extends Comparable<T>> implements Heap<T> {

    SkewHeapElement<T> root;

    /**
     * @param value
     * De waarde die aan de heap moet toegevoegd worden
     * @return
     * Het toegevoegde element
     */
    @Override
    public SkewHeapElement<T> insert(T value) {
        SkewHeapElement<T> l = new SkewHeapElement<>(value, this);

        if (root != null) {
            // Als de heap al elementen bevat: maak een nieuwe heap met het element en merge
            SkewHeap<T> h2 = new SkewHeap<>();
            h2.root = l;
            root = merge(root, h2.root);
        } else {
            // Maak anders l het eerste element van de heap
            root = l;
        }

        // Geef het aangemaakte element terug
        return l;
    }

    /**
     * @return
     * Het kleinste element van de heap
     * @throws EmptyHeapException
     * Wanneer de heap leeg is
     */
    @Override
    public SkewHeapElement<T> findMin() throws EmptyHeapException {
        if (root != null) {
            return root;
        } else {
            throw new EmptyHeapException();
        }
    }

    /**
     * @return
     * De waarde van het kleinste element van de heap
     * @throws EmptyHeapException
     * Wanneer de heap leeg is
     */
    @Override
    public T removeMin() throws EmptyHeapException {
        SkewHeapElement<T> min = findMin();

        // Root vervangen door de merge van zijn linker en rechterkind
        root = merge(min.left, min.right);
        if (root != null) {
            root.parent = null;
        }

        return min.value();
    }

    /**
     * Merged heap 1 met heap 2
     * @param t1
     * Heap 1
     * @param t2
     * Heap 2
     * @return
     * De top van de nieuwe heap
     */
    private SkewHeapElement<T> merge(SkewHeapElement<T> t1, SkewHeapElement<T> t2) {
        if (t1 == null) {
            return t2;
        } else if (t2 == null) {
            return t1;
        }

        SkewHeapElement<T> smallheap;
        SkewHeapElement<T> largeheap;

        if(t1.value.compareTo(t2.value) < 0) {
            smallheap = t1;
            largeheap = t2;
        } else {
            smallheap = t2;
            largeheap = t1;
        }

        // Kinderen wortel smallheap wisselen
        SkewHeapElement<T> tmp = smallheap.left;
        smallheap.left = smallheap.right;
        smallheap.right = tmp;

        if (smallheap.left == null) {
            smallheap.left = largeheap;
            largeheap.parent = smallheap;
        } else {
            smallheap.left = merge(smallheap.left, largeheap);
            smallheap.left.parent = smallheap;
        }

        return smallheap;
    }

    /**
     * Verwijdert een element uit de heap
     * @param el
     * Het te verwijderen element
     */
    void remove(SkewHeapElement<T> el) {
        try {
            SkewHeapElement<T> parent = el.parent;

            // Kijken of ouder niet null is, als ouder null is willen we het bovenste
            // element verwijderen en kunnen we gewoon removemin toepassen
            if (parent != null) {
                // Linker en rechterkind te verwijderen element mergen
                SkewHeapElement<T> connect = merge(el.left, el.right);

                // Kijken of het te verwijderen element een linker of rechterkind is
                // daarna merge linker en rechterkind aan ouder verwijderd element schakelen
                if (parent.right != null && parent.right.equals(el)) {
                    parent.right = connect;
                    if (connect != null) {
                        connect.parent = parent;
                    }
                } else {
                    parent.left = connect;
                    if (connect != null) {
                        connect.parent = parent;
                    }
                }

                // Zorgen dat weer aan skew eigenschap voldoet
                if (parent.left == null && parent.right != null) {
                    parent.left = parent.right;
                    parent.right = null;
                }

            } else {
                removeMin();
            }
        } catch (EmptyHeapException ex) {
            System.out.println("error, shouldn't happen");
        }

    }
    
}
