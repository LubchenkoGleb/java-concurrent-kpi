package com.kpi.lubchenko.lab2;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball) {
        b = ball;
    }

    @Override
    public void run() {
        try {
            while (true) {
                b.move();//посчитать новое положение шарика
//                if(b.isInPocket()) {//если шарик попал в лузуу останавливаю поток
//                    interrupt();
//                }
                System.out.println("Thread name = " + Thread.currentThread().getName());
                Thread.sleep(b.getSpeed());//текущему потоку говоришь уснуть на заданное количество времени
//                Thread.yield();
            }
        } catch (InterruptedException ex) { }
    }
}