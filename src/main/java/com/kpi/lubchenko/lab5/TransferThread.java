package com.kpi.lubchenko.lab5;

class TransferThread extends Thread {
    private Bank bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 1000000;

    public TransferThread(Bank b, int from, int max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    public void run() {
        try {
            for (int i = 0; i < REPS; i++) {
                int toAccount = (int) (bank.size() * Math.random());
                int amount = (int) (maxAmount * Math.random() / REPS);
                bank.transfer(fromAccount, toAccount, amount);
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            System.out.println(e.getMessage());
        }
    }
}