package ru.otus;

public class ExampleThread extends Thread {

    private final Object monitor;

    public ExampleThread(String name, Object monitor) {
        super(name);
        this.monitor = monitor;
    }

    @Override
    public void run() {
        int i = 1;
        boolean flag = true;

        while (true) {
            synchronized (monitor) {
                System.out.println(getName() + ": " + i);
                i = flag ? (i + 1) : (i - 1);

                if (i == 1 || i == 10) {
                    flag = !flag;
                }

                try {
                    monitor.notify();
                    monitor.wait();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
