package heap.binary;

import heap.Element;

public class BinaryHeapElement<T extends Comparable<T>> implements Element<T> {

    private T val;

    // Referentie naar de heap
    private BinaryHeap heap;

    // Houdt de index bij waarop het element in de lijst (heap) is opgeslaan
    private int index;

    BinaryHeapElement(T val, BinaryHeap heap) {
        this.val = val;
        this.heap = heap;
    }


    /**
     * @return
     * De waarde van het element
     */
    @Override
    public T value() {
        return val;
    }


    /**
     * Verwijderd het element uit de heap
     */
    @Override
    public void remove() {
        // Het element uit de heap verwijderen
        heap.removeAt(index);
    }

    /**
     * Past de waarde van het element aan
     * @param value
     * De nieuwe waarde van het element
     */
    @Override
    public void update(T value) {
        // Waarde aanpassen
        this.val = value;
        // Zorgen dat het element op de juiste positie in de heap staat
        heap.fixHeap(index);
    }


    /**
     * Stelt de waarde van het element in de lijst in
     * @param index
     * De nieuwe index van het element
     */
    void changeIndex(int index) {
        this.index = index;
    }
}
