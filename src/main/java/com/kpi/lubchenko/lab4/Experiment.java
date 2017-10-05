package com.kpi.lubchenko.lab4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Experiment {
    private String name;
    private Long startTime;
    private Long endTime;

    public Experiment(String name) {
        this.name = name;
        this.startTime = System.nanoTime();
    }

    public void setEndTime() {
        this.endTime = System.nanoTime();
    }

    public Long getTime() {
        return endTime - startTime;
    }

    public static void compareExperiments(Experiment... experiments) {
        List<Experiment> experimentsList = Arrays.asList(experiments);
        List<Experiment> collect = experimentsList.stream()
                .sorted(Comparator.comparing(Experiment::getTime)).collect(Collectors.toList());

        System.out.println("#1 " + collect.get(0));

        for (int i = 1; i < collect.size(); i++) {
            System.out.println("#" + (i + 1) + " " + collect.get(i));
        }
        System.out.println("=================================\n");
    }

    @Override
    public String toString() {
        return "time='" + this.getTime() + "', name='" + name + "'";
    }
}
