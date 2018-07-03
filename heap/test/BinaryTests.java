package heap.test;

import heap.EmptyHeapException;
import heap.binary.BinaryHeap;
import heap.binary.BinaryHeapElement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Bevat test voor de binary heap
 */
public class BinaryTests extends GeneralTest {

    @Test
    public void testBinaryComplexity() throws EmptyHeapException {
        ArrayList<Double> removeResults = new ArrayList<>();
        ArrayList<Double> updateResults = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            BinaryHeap<Integer> h1 = new BinaryHeap<>();
            removeResults.add(testRemoveComplexity(h1, 100));
            BinaryHeap<Integer> h2 = new BinaryHeap<>();
            updateResults.add(testUpdateComplexity(h2, 100));
        }
        System.out.println(calculateAverage(removeResults));
        System.out.println(calculateAverage(updateResults));
    }

    @Test
    public void testBinary() throws EmptyHeapException {
        Random rand = new Random();

        BinaryHeap<Integer> heap = new BinaryHeap<>();
        BinaryHeapElement<Integer> a = heap.insert(19);
        BinaryHeapElement<Integer> b = heap.insert(13);
        BinaryHeapElement<Integer> c = heap.insert(8);
        BinaryHeapElement<Integer> d = heap.insert(11);
        BinaryHeapElement<Integer> e = heap.insert(1);
        BinaryHeapElement<Integer> f = heap.insert(6);
        BinaryHeapElement<Integer> g = heap.insert(7);

        d.update(0);
        e.update(1);
        c.remove();

        System.out.println(testAscending(heap, 6));
    }

}
