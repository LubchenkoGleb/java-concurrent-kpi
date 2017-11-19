package com.kpi.lubchenko.lab6;

import lombok.Data;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Data
public class CubbyHole {

    private Integer values;
    private BlockingQueue<Integer> queue;
//    private Boolean producing = true;
    private Integer failures = 0;

    public CubbyHole(Integer values) {
        this.values = values;
        this.queue = new ArrayBlockingQueue<>(values);
    }

    public void put(Integer data) {
        if (queue.size() == values) {
            failures++;
        }
        try {
            queue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Integer get() {
        try {
            return queue.poll(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getSize() {
        return queue.size();
    }
}
