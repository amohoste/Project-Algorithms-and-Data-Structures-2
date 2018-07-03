package heap.test;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;
import heap.timer.Timer;

import java.util.ArrayList;
import java.util.Random;


/**
 * Klasse met algemente testfuncties
 */
class GeneralTest {

    /**
     * Berekent de gemiddelde waarde van de elementen in een array:ist
     * @param results
     * De arraylist
     * @return
     * Het gemiddelde van de arraylist
     */
    double calculateAverage(ArrayList<Double> results) {
        Double sum = 0.0;
        if(!results.isEmpty()) {
            for (Double result : results) {
                sum += result;
            }
            return sum / results.size();
        }
        return sum;
    }

    /**
     * Meet de tijd van een remove bewerking in een heap met #aantal elementen
     * @param heap
     * Een initieel lege heap waaraan de elementen moeten toegevoegd worden
     * @param aantal
     * Het aantal elementen die aan de initieel lege heap moeten toegevoegd worden
     * alvorens een element te verwijderen
     * @return
     * De tijd nodig voor de remove bewerking
     * @throws EmptyHeapException
     * Gooit emptyheapexception op indieneen reeds verwijderd element verwijderd wordt
     */
    double testRemoveComplexity(Heap<Integer> heap, int aantal) throws EmptyHeapException {

        Random rand = new Random();
        Timer t = new Timer();
        // Random positie in de hoop waarop we een element verwijderen
        int operationPos = rand.nextInt(aantal);

        for (int i = 0; i < operationPos; i++) {
            heap.insert(rand.nextInt());

        }
        Element<Integer> a = heap.insert(rand.nextInt());
        for (int i = operationPos + 1; i < aantal; i++) {
            heap.insert(rand.nextInt());

        }

        t.start();
        a.remove();
        t.end();
        return t.delta();
    }

    /**
     * Meet de tijd van een remove bewerking in een heap met #aantal elementen
     * @param heap
     * Een initieel lege heap waaraan de elementen moeten toegevoegd worden
     * @param aantal
     * Het aantal elementen die aan de initieel lege heap moeten toegevoegd worden
     * alvorens een element te updaten
     * @return
     * De tijd nodig voor de update bewerking
     * @throws EmptyHeapException
     * Gooit emptyheapexception op indien er een reeds verwijderd element geüpdated wordt
     */
    double testUpdateComplexity(Heap<Integer> heap, int aantal) throws EmptyHeapException {

        Random rand = new Random();
        Timer t = new Timer();
        // Random positie in de hoop waarop we een element verwijderen
        int operationPos = rand.nextInt(aantal);

        for (int i = 0; i < operationPos; i++) {
            heap.insert(rand.nextInt());

        }
        Element<Integer> a = heap.insert(rand.nextInt());
        for (int i = operationPos + 1; i < aantal; i++) {
            heap.insert(rand.nextInt());

        }

        t.start();
        a.update(rand.nextInt());
        t.end();
        return t.delta();
    }

    /**
     * Gaat na of na het verwijderen van alle elementen uit de heap deze in stijgende volgorde zijn
     * teruggegeven.
     * @param h
     * De heap die getest moet worden
     * @param size
     * Het aantal elementen in deze heap
     * @return
     * Of alle elementen na verwijderen in stijgende volgorde zijn teruggegeven
     * @throws EmptyHeapException
     * Gooit een emptyheapexception op indien er uit een lege heap verwijderd wordt
     */
    boolean testAscending(Heap<Integer> h, int size) throws EmptyHeapException {
        int cmp = h.removeMin();
        for (int i = 1; i < size; i++) {
            int tmp = h.removeMin();
            if (tmp < cmp ) {
                return false;
            } else {
                cmp = tmp;
            }
        }
        return true;
    }

}
