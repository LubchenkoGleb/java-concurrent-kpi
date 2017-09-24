package com.kpi.lubchenko.lab3;

public class Task3 {

    public static void main(String[] args) throws InterruptedException {
        int iterations = 10000000;
        UnsyncCounter unsyncCounter = new UnsyncCounter();

        int counter;
        Thread unsyncInc = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                unsyncCounter.inc();
            }
        });
        Thread unsyncDec = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                unsyncCounter.dec();
            }
        });
        unsyncInc.start();
        unsyncDec.start();
        unsyncInc.join();
        unsyncDec.join();

        System.out.println("unsyncCounter=" + unsyncCounter.counter);



        SyncCounter syncCounter = new SyncCounter();

        Thread syncInc = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                syncCounter.inc();
            }
        });
        Thread syncDec = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                syncCounter.dec();
            }
        });
        syncInc.start();
        syncDec.start();
        syncInc.join();
        syncDec.join();

        System.out.println("syncCounter=" + syncCounter.counter);
    }

    static class SyncCounter implements Counter {
        int counter;

        @Override
        synchronized public void inc() {
            counter++;
        }

        @Override
        synchronized public void dec() {
            counter--;
        }
    }

    static class UnsyncCounter implements Counter {
        int counter;

        @Override
        public void inc() {
            counter++;
        }

        @Override
        public void dec() {
            counter--;
        }
    }

    interface Counter {
        void inc();
        void dec();
    }
}
