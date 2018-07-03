package heap.test;

import heap.EmptyHeapException;
import heap.binomial.BinomialHeap;
import heap.binomial.BinomialHeapElement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Bevat test voor de binomial heap
 */
public class BinomialTests extends GeneralTest {

    @Test
    public void testBinomialComplexity() throws EmptyHeapException {
        ArrayList<Double> removeResults = new ArrayList<>();
        ArrayList<Double> updateResults = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            BinomialHeap<Integer> h1 = new BinomialHeap<>();
            removeResults.add(testRemoveComplexity(h1, 100));
            BinomialHeap<Integer> h2 = new BinomialHeap<>();
            updateResults.add(testUpdateComplexity(h2, 100));


        }
        System.out.println(calculateAverage(removeResults));
        System.out.println(calculateAverage(updateResults));
    }

    @Test
    public void testBinomial() throws EmptyHeapException {
        Random rand = new Random();

        BinomialHeap<Integer> heap = new BinomialHeap<>();
        BinomialHeapElement<Integer> a = heap.insert(19);
        BinomialHeapElement<Integer> b = heap.insert(13);
        BinomialHeapElement<Integer> c = heap.insert(8);
        BinomialHeapElement<Integer> d = heap.insert(11);
        BinomialHeapElement<Integer> e = heap.insert(1);
        BinomialHeapElement<Integer> f = heap.insert(6);
        BinomialHeapElement<Integer> g = heap.insert(7);

        d.update(0);
        e.update(1);
        c.remove();

        System.out.println(testAscending(heap, 6));
    }

    @Test
    public void testBinomial2() throws EmptyHeapException {
        Random rand = new Random();

        BinomialHeap<Integer> heap = new BinomialHeap<>();
        BinomialHeapElement<Integer> a = heap.insert(1000);
        BinomialHeapElement<Integer> b = heap.insert(1000);
        BinomialHeapElement<Integer> c = heap.insert(1000);
        BinomialHeapElement<Integer> d = heap.insert(1000);
        BinomialHeapElement<Integer> e = heap.insert(1000);
        BinomialHeapElement<Integer> f = heap.insert(1000);
        BinomialHeapElement<Integer> g = heap.insert(1000);

        a.update(4);
        b.update(6);
        c.update(0);
        d.update(5);
        d.update(100001);
        e.update(3);
        f.update(10);
        f.remove();
        g.update(1);

        for (int i = 0; i < 6; i++) {
            System.out.println(heap.removeMin());
        }
    }

}
