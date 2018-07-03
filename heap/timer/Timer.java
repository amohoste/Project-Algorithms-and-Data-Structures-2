package heap.timer;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amoryhoste
 */
public class Timer {

    private long time0, time1;
    private boolean ended = false;

    public Timer() {
        this.time0 = System.nanoTime();
        this.time1 = time0;
    }
    public void start() { time0 = System.nanoTime(); ended = false; }
    public void end()   { time1 = System.nanoTime(); ended = true; }
    public double delta() {
        if ( ! ended) { end(); }
        return 1e-9 * (time1 - time0); }    // elapsed time in seconds
}


