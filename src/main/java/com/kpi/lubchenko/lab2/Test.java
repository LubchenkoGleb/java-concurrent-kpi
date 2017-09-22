package com.kpi.lubchenko.lab2;

public class Test {

    public static void main(String[] args) {
        Runnable low = () -> {
            int a = 1;
            long start = System.currentTimeMillis();
            for (int i = 1; i < 100000000; i++) {
                a *= i * a * a * a * a * a;
            }
            long end = System.currentTimeMillis();
            System.out.println("low res = " + (end - start) + ", a = " + a + ", start = " + start);
        };

        Runnable high = () -> {
            int a = 1;
            long start = System.currentTimeMillis();
            for (int i = 1; i < 100000000; i++) {
                a *= i * a * a * a * a * a;
            }
            long end = System.currentTimeMillis();
            System.out.println("high res = " + (end - start) + ", a = " + a + ", start = " + start);
        };

        Thread highPriority = new Thread(high);
        highPriority.setPriority(Thread.MIN_PRIORITY);
        highPriority.start();

        for (int i = 0; i < 100; i++) {
            Thread lowPrior = new Thread(low);
            lowPrior.setPriority(Thread.MAX_PRIORITY);
            lowPrior.start();
        }

    }
}
