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
                b.move();
                Thread.sleep(b.getSpeed());
            }
        } catch (InterruptedException ex) { }
    }
}