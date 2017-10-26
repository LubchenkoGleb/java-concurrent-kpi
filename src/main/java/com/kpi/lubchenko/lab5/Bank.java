package com.kpi.lubchenko.lab5;

class Bank {

    public static final int NTEST = 1000000;
    private final int[] accounts;
    private long ntransacts = 0;
        accounts = new int[n];
        for (int i = 0; i < account
s.length; i++) {
            accounts[i] = initialBalance;
        }
    }

    public synchronized void transfer(int from, int to, int amount) throws InterruptedException {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0) {
            test();
        }
    }

    public void test() {
        int sum = 0;
        for (int account : accounts) {
            sum += account;
        }
        System.out.println("Transactions :" + ntransacts + " Sum: " + sum);
    }

    public int size() {
        return accounts.length;
    }
}