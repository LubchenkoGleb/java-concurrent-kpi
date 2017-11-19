package com.kpi.lubchenko.test;

public class Main {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> Holder.task.task("1"));
        Thread thread2 = new Thread(() -> Holder.task.task("2"));
        thread1.start();
        thread2.start();
    }
}
