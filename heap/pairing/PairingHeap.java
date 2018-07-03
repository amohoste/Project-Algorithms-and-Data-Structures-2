package heap.pairing;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class PairingHeap<T extends Comparable<T>> implements Heap<T> {

    PairingHeapElement<T> root;

    public PairingHeap() {
        root = null;
    }

    /**
     * @param value
     * De waarde die aan de heap moet toegevoegd worden
     * @return
     * Het toegevoegde element
     */
    @Override
    public PairingHeapElement<T> insert(T value) {
        PairingHeapElement<T> l = new PairingHeapElement<>(value, this);

        if (root != null) {
            // Als de heap al elementen bevat: maak een nieuwe heap met het element en merge
            root = merge(root, l);
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
    public PairingHeapElement<T> findMin() throws EmptyHeapException {
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
        PairingHeapElement<T> min = findMin();

        root = removeSubTree(min);

        return min.value();
    }

    /**
     * Verwijderd de wortel van de deelboom met wortel root en geeft de nieuwe
     * deelboom na deze verwijderbewerking terug
     * @param root
     * De wortel van de deelboom
     * @return
     * Het resultaat van de verwijderbewerking
     */
    PairingHeapElement<T> removeSubTree(PairingHeapElement<T> root) {
        // Nieuwe heap
        PairingHeapElement<T> result;

        if (root.child != null) {
            LinkedList<PairingHeapElement<T>> stack = new LinkedList<>();

            // Van links naar rechts over kinderen verwijderd el gaan en deze 2 aan 2 mergen, daarna op stack pushen
            PairingHeapElement<T> curr = root.child;
            while (curr != null && curr.next != null) {
                curr.previous = null;
                curr.next.previous = null;
                PairingHeapElement<T> volg = curr.next.next;
                stack.push(merge(curr, curr.next));
                curr = volg;
            }

            // Als het aantal kinderen oneven is, maak dit het tijdelijk resultaat
            if (curr != null) {
                result = curr;
                result.previous = null;
            } else {
                // Maak anders het tijdelijk resultaat het eerste element van de stack
                result = stack.pop();
            }

            // Zolang stack niet leeg is pop element van stack en merge dit met resultaat
            while(stack.size() != 0) {
                PairingHeapElement<T> el1 = stack.pop();
                result = merge(el1, result);
            }
        } else {
            result = null;
        }
        return result;
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
    PairingHeapElement<T> merge(PairingHeapElement<T> t1, PairingHeapElement<T> t2) {
        if (t1 == null) {
            return t2;
        } else if(t2 == null) {
            return t1;
        }

        // Als t1 kleiner is dan t2 wordt t2 het linkerkind van t1
        if (t1.value.compareTo(t2.value) < 0) {
            t2.previous = t1;
            t2.next = t1.child;
            if (t2.next != null) {
                t2.next.previous = t2;
            }
            t1.child = t2;
            return t1;
        } else {
            t1.previous = t2;
            t1.next = t2.child;
            if (t1.next != null) {
                t1.next.previous = t1;
            }
            t2.child = t1;
            return t2;
        }

    }
}
