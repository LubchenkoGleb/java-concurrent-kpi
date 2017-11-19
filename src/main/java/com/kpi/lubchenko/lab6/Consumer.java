package com.kpi.lubchenko.lab6;

public class Consumer implements Runnable {

    private CubbyHole cubbyHole;

    public Consumer(CubbyHole cubbyHole) {
        this.cubbyHole = cubbyHole;
    }

    @Override
    public void run() {
        Integer data = cubbyHole.get();

        while (/*cubbyHole.getProducing() || */data != null) {
//            System.out.println("Consumed : " + data);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data = cubbyHole.get();
        }
    }
}
