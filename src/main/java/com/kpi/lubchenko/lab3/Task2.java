package com.kpi.lubchenko.lab3;

public class Task2 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("syncTable");
        SyncTable syncTable = new SyncTable();

        Thread thread1 = new Thread(new RunnableImpl("+", syncTable));
        thread1.start();

        Thread thread2 = new Thread(new RunnableImpl("-", syncTable));
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("unsyncTable");
        UnsyncTable unsyncTable = new UnsyncTable();

        Thread unsyncThread1 = new Thread(new RunnableImpl("+", unsyncTable));
        unsyncThread1.start();

        Thread unsyncThread2 = new Thread(new RunnableImpl("-", unsyncTable));
        unsyncThread2.start();
    }

    static class RunnableImpl implements Runnable {
        String symbol;
        Table table;

        public RunnableImpl(String symbol, Table syncTable) {
            this.symbol = symbol;
            this.table = syncTable;
        }

        @Override
        public void run() {
            while (table.getCounter() <= 100) {
                table.addSymbol(symbol);
            }
        }
    }

    static class SyncTable implements Table {
        int symbolsCounter = 0;
        int linesCounter = 0;

        @Override
        public synchronized void addSymbol(String symbol) {
            System.out.print(symbol);
            symbolsCounter++;

            if (symbolsCounter % 100 == 0) {
                System.out.println();
                linesCounter++;
            }

            this.notifyAll();

            if (linesCounter > 100) {
                return;
            }

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getCounter() {
            return linesCounter;
        }
    }

    static class UnsyncTable implements Table {
        int symbolsCounter = 0;
        int linesCounter = 0;

        @Override
        public void addSymbol(String symbol) {
            System.out.print(symbol);
            symbolsCounter++;

            if (symbolsCounter % 100 == 0) {
                System.out.println();
                linesCounter++;
            }
        }

        @Override
        public int getCounter() {
            return linesCounter;
        }
    }

    interface Table {
        void addSymbol(String symbol);

        int getCounter();
    }
}
