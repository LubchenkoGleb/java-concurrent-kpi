package com.kpi.lubchenko.lab6;

import java.util.Random;

public class Producer implements Runnable{

    private CubbyHole cubbyHole;
    private Integer values;

    public Producer(CubbyHole cubbyHole, Integer values) {
        this.cubbyHole = cubbyHole;
        this.values = values;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < values; i++) {
//            System.out.println("Produced: " + i);
            try {
                Thread.sleep(25 + random.nextInt(75));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cubbyHole.put(i);
        }
//        cubbyHole.setProducing(false);
//        System.out.println("Producer finish");
    }
}
