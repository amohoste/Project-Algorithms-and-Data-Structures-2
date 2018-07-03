package heap.test;

import heap.EmptyHeapException;
import heap.skew.SkewHeap;
import heap.skew.SkewHeapElement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Bevat test voor de skew heap
 */
public class SkewTests extends GeneralTest {

    @Test
    public void testSkew() throws EmptyHeapException {
        Random r = new Random();
        ArrayList<SkewHeapElement<Integer>> a = new ArrayList<>();
        SkewHeap<Integer> heap = new SkewHeap<>();
        for (int i = 0; i < 1000; i++) {
            a.add(heap.insert(r.nextInt()));
        }
        for (SkewHeapElement<Integer> l : a) {
            l.update(r.nextInt());
        }

        System.out.println(testAscending(heap, 1000));
    }


    @Test
    public void testSkew2() throws EmptyHeapException {
        Random rand = new Random();

        SkewHeap<Integer> heap = new SkewHeap<>();
        SkewHeapElement<Integer> a = heap.insert(1000);
        SkewHeapElement<Integer> b = heap.insert(1000);
        SkewHeapElement<Integer> c = heap.insert(1000);
        SkewHeapElement<Integer> d = heap.insert(1000);
        SkewHeapElement<Integer> e = heap.insert(1000);
        SkewHeapElement<Integer> f = heap.insert(1000);
        SkewHeapElement<Integer> g = heap.insert(1000);

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

        System.out.println(testAscending(heap, 5));
    }

    @Test
    public void testSkewComplexity() throws EmptyHeapException {
        ArrayList<Double> removeResults = new ArrayList<>();
        ArrayList<Double> updateResults = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            SkewHeap<Integer> h1 = new SkewHeap<>();
            removeResults.add(testRemoveComplexity(h1, 100));
            SkewHeap<Integer> h2 = new SkewHeap<>();
            updateResults.add(testUpdateComplexity(h2, 100));
        }
        System.out.println(calculateAverage(removeResults));
        System.out.println(calculateAverage(updateResults));
    }

}
