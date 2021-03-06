package com.kpi.lubchenko.lab5.task2;

class TransferThread extends Thread {

    private Bank bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 1000;

    public TransferThread(Bank b, int from, int max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    public void run() {
        try {
            while (!interrupted()) {
                for (int i = 0; i < REPS; i++) {
                    int toAccount = (int) (bank.size() * Math.random());
                    int amount = (int) (maxAmount * Math.random() / REPS);
                    bank.transfer(fromAccount, toAccount, amount);
                    Thread.sleep(1);
                }
            }
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }
}