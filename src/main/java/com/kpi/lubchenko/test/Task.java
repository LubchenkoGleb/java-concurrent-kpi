package com.kpi.lubchenko.test;

public class Task {

    public void task(String str) {
        for (int i = 0; i < 100000; i++) {
            System.out.println(str);
//            try {
//                Thread.currentThread().sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
