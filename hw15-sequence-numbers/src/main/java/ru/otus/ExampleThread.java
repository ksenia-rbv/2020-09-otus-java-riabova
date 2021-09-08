package ru.otus;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ExampleThread extends Thread {

    private final int num;
    private final CyclicBarrier cyclicBarrier;

    public ExampleThread(int num, CyclicBarrier cyclicBarrier) {
        super("Thread" + num);
        this.num = num;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        int i = 1;
        boolean flag = true;

        while (true) {
            if (num != cyclicBarrier.getNumberWaiting() + 1) {
                delay(10);
            } else {
                System.out.println(getName() + ": " + i);
                i = flag ? (i + 1) : (i - 1);

                if (i == 1 || i == 10) {
                    flag = !flag;
                }
                awaitBarrier();
                delay(500);
            }
        }
    }

    private void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void awaitBarrier() {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
