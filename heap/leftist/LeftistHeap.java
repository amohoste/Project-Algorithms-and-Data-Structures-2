package heap.leftist;

import heap.EmptyHeapException;
import heap.Heap;
import java.lang.Math;

public class LeftistHeap<T extends Comparable<T>> implements Heap<T> {

    LeftistHeapElement<T> root;

    public LeftistHeap() {
        root = null;
    }

    /**
     * @param value
     * De waarde die aan de heap moet toegevoegd worden
     * @return
     * Het toegevoegde element
     */
    @Override
    public LeftistHeapElement<T> insert(T value) {
        LeftistHeapElement<T> l = new LeftistHeapElement<>(value, this);

        if (root != null) {
            // Als de heap al elementen bevat: maak een nieuwe heap met het element en merge
            LeftistHeap<T> h2 = new LeftistHeap<>();
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
    public LeftistHeapElement<T> findMin() throws EmptyHeapException {
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
        LeftistHeapElement<T> min = findMin();

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
    private LeftistHeapElement<T> merge(LeftistHeapElement<T> t1, LeftistHeapElement<T> t2) {
        // Basisgevallen recursie
        if (t1 == null) {
            return t2;
        } else if (t2 == null) {
            return t1;
        }

        // Als waarde t1 groter is dan waarde t2: omwisselen
        if (t1.value.compareTo(t2.value) > 0) {
            LeftistHeapElement<T> tmp = t1;
            t1 = t2;
            t2 = tmp;
        }

        // Recursiestap
        t1.right = merge(t1.right, t2);
        t1.right.parent = t1;

        // Als npl linkerkind kleiner is dan linkerkind omwisselen
        if (npl(t1.left) < npl(t1.right)) {
            LeftistHeapElement<T> tmp = t1.left;
            t1.left = t1.right;
            t1.right = tmp;
        }
        // Npl updaten
        t1.npl = npl(t1.right) + 1;

        return t1;
    }


    /**
     * @param l
     * Het element waarvan we de npl willen weten
     * @return
     * De nullpadlengte van dit element
     */
    private int npl(LeftistHeapElement<T> l) {
        if (l == null) {
            return -1;
        } else {
            return l.npl;
        }
    }

    /**
     * Verwijdert een element uit de heap
     * @param el
     * Het te verwijderen element
     */
    void remove(LeftistHeapElement<T> el) {
        try {
            LeftistHeapElement<T> parent = el.parent;

            // Kijken of ouder niet null is, als ouder null is willen we het bovenste
            // element verwijderen en kunnen we gewoon removemin toepassen
            if (parent != null) {
                // Linker en rechterkind te verwijderen element mergen
                LeftistHeapElement<T> connect = merge(el.left, el.right);

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

                // Npl updaten vanaf ouder verwijderde element en leftist herstellen indien nodig
                LeftistHeapElement<T> current = parent;
                while (current != null) {
                    // Nieuwe npl berekenen
                    int newnpl = Math.min(npl(current.left), npl(current.right)) + 1;

                    // Kijken of npl verschilt van oude npl
                    if (current.npl != newnpl) {
                        // Indien verschilt npl updaten
                        current.npl = newnpl;

                        // Kijken of voldoet aan leftist en indien nodig swap
                        if (npl(current.left) < npl(current.right)) {
                            LeftistHeapElement<T> tmp = current.left;
                            current.left = current.right;
                            current.right = tmp;
                        }
                    } else {
                        // Als npl niet meer veranderd kunnen we stoppen, de npl's erboven kloppen dan
                        // al en de overeenkomstige el voldoen dus ook al aan leftist eigenschap
                        break;
                    }
                    current = current.parent;
                }

            } else {
                removeMin();
            }
        } catch (EmptyHeapException ex) {
            System.out.println("error, shouldn't happen");
        }

    }

}
