package heap.binomial;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

public class BinomialHeap<T extends Comparable<T>> implements Heap<T> {

    private BinomialHeapNode<T> head;

    public BinomialHeap() {
        head = null;
    }

    /**
     * @param value
     * De waarde die aan de heap moet toegevoegd worden
     * @return
     * Het toegevoegde element
     */
    @Override
    public BinomialHeapElement<T> insert(T value) {
        BinomialHeapNode<T> b = new BinomialHeapNode<>(value, this);
        if (head != null) {
            // Als de heap al elementen bevat: maak een nieuwe heap met het element en merge
            BinomialHeap<T> h2 = new BinomialHeap<>();
            h2.head = b;
            mergeWith(h2);
        } else {
            // Maak anders b het eerste element van de heap
            head = b;
        }

        // Geef het BinomialHeapElement terug dat overeenkomt met deze node
        return b.el;
    }

    /**
     * @return
     * Het kleinste element van de heap
     * @throws EmptyHeapException
     * Wanneer de heap leeg is
     */
    @Override
    public BinomialHeapElement<T> findMin() throws EmptyHeapException {

        // Kijken of eerste element niet leeg is
        if (head != null) {
            // Hulpvariabelen om kleinste te vinden
            BinomialHeapNode<T> min = head;
            BinomialHeapNode<T> current = head;

            // Alle toppen overlopen en kleinste steeds aanpassen
            while (current != null) {
                if (current.sibling != null && (current.sibling.value.compareTo(min.value) < 0)) {
                    min = current.sibling;
                }
                current = current.sibling;
            }

            return min.el;
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

        // Kijken of heap niet leeg is
        if (head != null) {
            // Hulpvariabelen om kleinste te vinden
            BinomialHeapNode<T> min = head;
            BinomialHeapNode<T> current = head;

            // Element voor kleinste element, volgend element van dit element wijzigen naar
            // volgende element kleinste element om kleinste element uit de heap te verwijderen
            BinomialHeapNode<T> prev = head;

            // Kleinste zoeken
            while (current != null) {
                if (current.sibling != null && (current.sibling.value.compareTo(min.value) < 0)) {
                    min = current.sibling;
                    prev = current;
                }
                current = current.sibling;
            }

            // Kleinste element verwijderen
            if(min == head) {
                // Als minimale element vooraan staat kunnen we dit eenvoudig verwijderen
                head = head.sibling;
            } else {
                // Volgende element van element voor min wordt element na min
                prev.sibling = min.sibling;
            }

            // Elementen onder kleinste element vormen nieuwe hoop (eerst omdraaien zodat in
            // stijgende graden). Daarna mergen met de huidige heap.
            BinomialHeap<T> h2 = new BinomialHeap<>();
            h2.head = reverseHeap(min.lchild);
            mergeWith(h2);

            // Geef waarde kleinste element terug (2x value aangezien in valuewrapper)
            return min.value.value;
        } else {
            throw new EmptyHeapException();
        }
    }

    /**
     * Keert een binomiale heap om
     * @param first
     * Het eerste element van de om te keren heap
     * @return
     * Het eerste element van de omgekeerde heap
     */
    private BinomialHeapNode<T> reverseHeap(BinomialHeapNode<T> first) {
        // Hulpvariabelen
        BinomialHeapNode<T> prev = null;
        BinomialHeapNode<T> current = first;
        BinomialHeapNode<T> next;

        while (current != null) {
            current.parent = null;
            next = current.sibling;
            current.sibling = prev;
            prev = current;
            current = next;
        }
        return prev;
    }


    /**
     * Schakelt twee binomiale bomen aan elkaar
     * @param newChild
     * Het kind
     * @param newParent
     * De ouder
     */
    private void connectBinomialTrees(BinomialHeapNode<T> newChild, BinomialHeapNode<T> newParent) {
        newChild.parent = newParent;
        newChild.sibling = newParent.lchild;
        newParent.lchild = newChild;
        newParent.degree += 1;
    }


    /**
     * Schakelt heap 2 aan de huidige heap met de bomen gesorteerd volgens stijgende graad van de wortel
     * @param h2
     * De heap die we aan de huidige heap schakelen
     */
    private void connectHeaps(BinomialHeap<T> h2) {

        // Het element waar naar wordt gekeken
        BinomialHeapNode<T> current1 = head;
        BinomialHeapNode<T> current2 = h2.head;

        if (current1 == null) {
            // Als eerste heap leeg is kunnen we deze gewoon vervangen door de tweede heap
            head = h2.head;
        } else if (current2 != null) {
            // Eerste element van het resultaat
            BinomialHeapNode<T> resultHead;

            // Eerste boom resultaat zoeken
            if (current1.degree < current2.degree) {
                resultHead = current1;
                // Naar volgende boom heap gaan
                current1 = current1.sibling;
            } else {
                resultHead = current2;
                // Naar volgende boom heap gaan
                current2 = current2.sibling;
            }

            // Laatst toegevoegde boom van het resultaat
            BinomialHeapNode<T> r = resultHead;

            while (current1 != null && current2 != null) {

                if (current1.degree < current2.degree) {
                    r.sibling = current1;
                    current1 = current1.sibling;
                } else {
                    r.sibling = current2;
                    current2 = current2.sibling;
                }
                // Laatst toegevoegde boom resultaat 1 opschuiven
                r = r.sibling;
            }

            // Als eerste hoop leeg is, schakel rest van tweede hoop aan resultaat anders is de
            // tweede hoop leeg en schakelen we de resterende eerste hoop aan het resultaat
            if (current1 == null) {
                r.sibling = current2;
            } else {
                r.sibling = current1;
            }

            // Vervang de huidige heap door de nieuwe gesorteerde hoop
            head = resultHead;
        }

    }


    /**
     * Merged de huidige heap met heap 2 (h2)
     * @param h2
     * De heap waarme we onze heap willen mergen
     */
    private void mergeWith(BinomialHeap<T> h2) {

        // Zorgt ervoor dat alle elementen van onze heap en h2 achter elkaar
        // gesorteerd staan volgens stijgende graad
        connectHeaps(h2);

        // Controleer of onze hoop niet leeg is
        if (head != null) {
            // Hulpvariabelen
            BinomialHeapNode<T> prev = null;
            BinomialHeapNode<T> current = head;
            BinomialHeapNode<T> next = head.sibling;


            while (next != null) {

                // Wanneer de huidige boom dezelfde graad is als de twee volgende of wanneer de graden van de
                // huidig beschouwde boom en volgende boom niet gelijk zijn kunnen we een plaats opschuiven
                if ((next.sibling != null && current.degree == next.degree && current.degree == next.sibling.degree) || current.degree != next.degree) {
                    prev = current;
                    current = next;
                } else {
                    // Graden huidige top en volgende top moeten gelijk zijn

                    // Als top huidige boom <= top volgende boom
                    if (current.value.compareTo(next.value) < 1) {
                        // Volgende boom aan huidige boom schakelen
                        current.sibling = next.sibling;
                        connectBinomialTrees(next, current);
                    } else {
                        // Kijken of vorig element niet leeg is en huidige boom aan volgende
                        // boom schakelen
                        if (prev != null) {
                            prev.sibling = next;
                        } else {
                            head = next;
                        }
                        connectBinomialTrees(current, next);
                        current = next;
                    }
                }
                // Next aanpassen
                next = current.sibling;
            }
        }

    }

}
