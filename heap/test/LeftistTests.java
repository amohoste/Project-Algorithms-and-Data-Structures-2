package heap.test;

import heap.EmptyHeapException;
import heap.leftist.LeftistHeap;
import heap.leftist.LeftistHeapElement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Bevat test voor de leftist heap
 */
public class LeftistTests extends GeneralTest {

    @Test
    public void testLeftist() throws EmptyHeapException {
        Random rand = new Random();

        LeftistHeap<Integer> heap = new LeftistHeap<>();
        LeftistHeapElement<Integer> a = heap.insert(19);
        LeftistHeapElement<Integer> b = heap.insert(13);
        LeftistHeapElement<Integer> c = heap.insert(8);
        LeftistHeapElement<Integer> d = heap.insert(11);
        LeftistHeapElement<Integer> e = heap.insert(1);
        LeftistHeapElement<Integer> f = heap.insert(6);
        LeftistHeapElement<Integer> g = heap.insert(7);

        d.update(0);
        e.update(1);
        c.remove();

        System.out.println(testAscending(heap, 6));
    }

    @Test
    public void testLeftist2() throws EmptyHeapException {
        Random rand = new Random();

        LeftistHeap<Integer> heap = new LeftistHeap<>();
        LeftistHeapElement<Integer> a = heap.insert(1000);
        LeftistHeapElement<Integer> b = heap.insert(1000);
        LeftistHeapElement<Integer> c = heap.insert(1000);
        LeftistHeapElement<Integer> d = heap.insert(1000);
        LeftistHeapElement<Integer> e = heap.insert(1000);
        LeftistHeapElement<Integer> f = heap.insert(1000);
        LeftistHeapElement<Integer> g = heap.insert(1000);

        a.update(4);
        b.update(6);
        c.update(0);
        d.update(5);
        d.update(100001);
        e.update(3);
        e.remove();
        f.update(10);
        f.remove();
        g.update(1);

        for (int i = 0; i <  5; i++) {
            System.out.println(heap.removeMin());
        }
    }

    @Test
    public void testLeftistComplexity() throws EmptyHeapException {
        ArrayList<Double> removeResults = new ArrayList<>();
        ArrayList<Double> updateResults = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            LeftistHeap<Integer> h1 = new LeftistHeap<>();
            removeResults.add(testRemoveComplexity(h1, 100000));
            LeftistHeap<Integer> h2 = new LeftistHeap<>();
            updateResults.add(testUpdateComplexity(h2, 100000));
        }
        System.out.println(calculateAverage(removeResults));
        System.out.println(calculateAverage(updateResults));
    }

}
