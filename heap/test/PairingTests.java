package heap.test;

import heap.EmptyHeapException;
import heap.pairing.PairingHeap;
import heap.pairing.PairingHeapElement;
import heap.timer.Timer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Bevat test voor de pairing heap
 */
public class PairingTests extends GeneralTest {

    @Test
    public void testPairing1() throws EmptyHeapException {
        Random rand = new Random();

        PairingHeap<Integer> heap = new PairingHeap<>();
        heap.insert(19);
        heap.insert(13);
        heap.insert(8);
        heap.insert(11);
        heap.insert(1);
        heap.insert(6);
        heap.insert(7);

        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());

        heap.insert(11);
        heap.insert(1);
        heap.insert(6);
        heap.insert(7);
        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());


    }

    @Test
    public void testPairing2() throws EmptyHeapException {
        Random rand = new Random(21312);
        Timer t = new Timer();

        PairingHeap<Integer> heap = new PairingHeap<>();
        t.start();
        for (int i = 0; i < 1000000; i++) {
            heap.insert(rand.nextInt(12312));
        }
        t.end();
        System.out.println(t.delta());

        t.start();
        for (int i = 0; i < 1000000; i++) {
            heap.removeMin();
        }
        t.end();
        System.out.println(t.delta());

    }

    /*
     * Skew Heap
     */
    @Test
    public void testPairing3() throws EmptyHeapException {
        Random r = new Random();
        ArrayList<PairingHeapElement<Integer>> a = new ArrayList<>();
        PairingHeap<Integer> heap = new PairingHeap<>();
        for (int i = 0; i < 1000; i++) {
            a.add(heap.insert(r.nextInt()));
        }
        for (PairingHeapElement<Integer> l : a) {
            l.remove();
        }
    }

    @Test
    public void testPairing4() throws EmptyHeapException {
        Random rand = new Random();

        PairingHeap<Integer> heap = new PairingHeap<>();
        PairingHeapElement<Integer> a = heap.insert(1000);
        PairingHeapElement<Integer> b = heap.insert(1000);
        PairingHeapElement<Integer> c = heap.insert(1000);
        PairingHeapElement<Integer> d = heap.insert(1000);
        PairingHeapElement<Integer> e = heap.insert(1000);
        PairingHeapElement<Integer> f = heap.insert(1000);
        PairingHeapElement<Integer> g = heap.insert(1000);

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

        for (int i = 0; i < 5; i++) {
            System.out.println(heap.removeMin());
        }
    }

    @Test
    public void testPairing5() throws EmptyHeapException {
        Random r = new Random(2413);
        ArrayList<PairingHeapElement<Integer>> a = new ArrayList<>();
        PairingHeap<Integer> heap = new PairingHeap<>();
        for (int i = 0; i < 1000; i++) {
            a.add(heap.insert(r.nextInt()));
        }
        for (PairingHeapElement<Integer> l : a) {
            l.update(r.nextInt());
        }

        for (int i = 0; i < 1000; i++) {
            heap.removeMin();
        }
    }

    @Test
    public void testPairingComplexity() throws EmptyHeapException {
        ArrayList<Double> removeResults = new ArrayList<>();
        ArrayList<Double> updateResults = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            PairingHeap<Integer> h1 = new PairingHeap<>();
            removeResults.add(testRemoveComplexity(h1, 100));
            PairingHeap<Integer> h2 = new PairingHeap<>();
            updateResults.add(testUpdateComplexity(h2, 100));
        }
        System.out.println(calculateAverage(removeResults));
        System.out.println(calculateAverage(updateResults));
    }

}
