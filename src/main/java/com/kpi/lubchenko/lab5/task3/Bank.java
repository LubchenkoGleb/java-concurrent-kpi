package com.kpi.lubchenko.lab5.task3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {

    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    private Lock lock;

    public Bank(int n, int initialBalance) {
        this.lock = new ReentrantLock();
        accounts = new int[n];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
        }
    }

    public synchronized void transfer(int from, int to, int amount) throws InterruptedException {
        lock.lock();
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        lock.unlock();

        if (ntransacts % NTEST == 0) {
            test();
        }
    }

    public void test() {
        lock.lock();
        int sum = 0;
        for (int i = 0; i < accounts.length ; i++) {
            sum += accounts[i];
        }
        System.out.println("Transactions: " +ntransacts + " Sum: " +sum);
        lock.unlock();
    }

    public int size() {
        return accounts.length;
    }
}