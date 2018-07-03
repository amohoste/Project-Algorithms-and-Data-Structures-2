package heap.binary;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

import java.util.ArrayList;

public class BinaryHeap<T extends Comparable<T>> implements Heap<T> {

    public ArrayList<BinaryHeapElement<T>> heap;

    public BinaryHeap() {
        heap = new ArrayList<>();
    }


    /**
     * @param i
     * De index van het element
     * @return
     * De index van het linkerkind van i
     */
    private int lChild(int i) {
        return (2 * i) + 1;
    }

    /**
     * @param i
     * De index van het element
     * @return
     * De index van het rechterkind van i
     */
    private int rChild(int i) {
        return (2 * i) + 2;
    }

    /**
     * @param i
     * De index van het element
     * @return
     * De index van de ouder van i
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }


    /**
     * @param value
     * De waarde die aan de heap moet toegevoegd worden
     * @return
     * Het toegevoegde element
     */
    @Override
    public BinaryHeapElement<T> insert(T value) {
        // Nieuw element aanmaken met waarde en toevoegen aan de heap
        BinaryHeapElement<T> newel = new BinaryHeapElement<>(value,this);
        heap.add(newel);

        // Index nieuw element
        int i = heap.size() - 1;

        // Het element opwaards laten percolleren tot het op de juiste positie staat
        percolateUp(i);

        // Het aangemaakte element teruggeven
        return newel;
    }

    /**
     * @return
     * Het kleinste element van de heap
     * @throws EmptyHeapException
     * Wanneer de heap leeg is
     */
    @Override
    public BinaryHeapElement<T> findMin() throws EmptyHeapException {
        if (heap.size() > 0) {
            return heap.get(0);
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
        T kleinste = findMin().value();

        if (heap.size() > 1) {
            // Neem het laatste element van de hoop als nieuwe root en pas zijn index aan
            BinaryHeapElement<T> newRoot = heap.remove(heap.size() - 1);
            newRoot.changeIndex(0);
            heap.set(0, newRoot);

            // Laat dit element neerwaards percolleren zodat hij op de juiste positie terechtkomt
            percolateDown(0);
        } else {
            // Als de heap maar één element bevat verwijderen we dit gewoon
            heap.remove(0);
        }

        return kleinste;
    }

    /**
     * Verwisseld element el op index i telkens met zijn
     * kleinste kind zolang el groter is dan dit kind
     * @param i
     * De index van het te wisselen element
     */
    private void percolateDown(int i) {

        // Kijkt of het element op index i kinderen heeft
        if (lChild(i) < heap.size()) {
            // Het te verwisselen element
            BinaryHeapElement<T> el = heap.get(i);

            // Kleinste kind
            int c = kleinsteKind(i);
            BinaryHeapElement<T> child = heap.get(c);

            while ((lChild(i) < heap.size()) && (child.value().compareTo(el.value()) < 0)) {

                // Kind plaats naar onder schuiven en index aanpassen
                heap.set(i, child);
                child.changeIndex(i);

                // Nieuwe index en kind aanpassen
                i = c;

                if (lChild(i) < heap.size()) {
                    c = kleinsteKind(i);
                    child = heap.get(c);
                }
            }

            // Nieuw element op juiste plaats invoegen en index aanpassen
            heap.set(i, el);
            el.changeIndex(i);
        }
    }

    /**
     * Verwisseld element el op index i telkens met zijn
     * ouder zolang hij kleiner is dan zijn ouder
     * @param i
     * De index van het te wisselen element
     */
    private void percolateUp(int i) {
        // Het te wisselen element
        BinaryHeapElement<T> el = heap.get(i);

        BinaryHeapElement<T> parent = null;
        if (i > 0) {
            parent = heap.get(parent(i));
        }

        while ((i > 0) && (el.value().compareTo(parent.value()) < 0)) {
            // Ouder plaats naar onder schuiven en index aanpassen
            heap.set(i, parent);
            parent.changeIndex(i);

            // Nieuwe index en parent aanpassen
            i = parent(i);
            parent = heap.get(parent(i));
        }

        // Nieuw element op juiste plaats invoegen en index aanpassen
        heap.set(i, el);
        el.changeIndex(i);
    }

    /**
     * Bepaald de index van het kleinste kind van
     * een bepaald el met index i
     * @param i
     * Index i van een element
     * @return
     * Index kleinste kind van element met index i
     */
    private int kleinsteKind(int i) {
        // Index linker en rechterkind bepalen
        int left = lChild(i);
        int right = rChild(i);
        int smallest;

        // Kijken of element een rechterkind heeft
        if (right != heap.size()) {
            if (heap.get(left).value().compareTo(heap.get(right).value()) < 0) {
                smallest = left;
            } else {
                smallest = right;
            }
        } else {
            // Heeft enkel een linkerkind
            smallest = left;
        }

        return smallest;
    }

    /**
     * Past de positie aan van een element op positie i
     * (indien nodig), opgeroepen wanneer de waarde van een
     * BinaryHeapElement veranderd wordt
     * @param i
     * De index van element waarvan de positie moet aangepast worden
     */
    void fixHeap(int i) {
        // Als i kleiner is dan zijn ouder
        if ((i > 0) && heap.get(i).value().compareTo(heap.get(parent(i)).value()) < 0) {
            // Opwaards laten percolleren
            percolateUp(i);
        } else {
            // Neerwaards laten percolleren
            percolateDown(i);
        }
    }

    /**
     * Verwijderd het element op positie i uit de heap
     * @param i
     * De index van het element dat verwijderd moet worden
     */
    void removeAt(int i) {
        // Kijken of het element niet het laatste element zelf is
        if (i < heap.size() - 1) {
            // Vervang element op positie I door laatste element
            BinaryHeapElement<T> newI = heap.remove(heap.size() - 1);
            newI.changeIndex(i);
            heap.set(i, newI);
            // Zorgen dat heap weer voldoet aan voorwaarden binaire heap
            fixHeap(i);
        } else {
            heap.remove(i);
        }
    }

}
