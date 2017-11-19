package com.kpi.lubchenko.lab6;


import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        ArrayList<Pair> result = main.startExperiment(5, 3, 200, 2);

        double averageSize = result.stream().mapToDouble(Pair::getKey).average().getAsDouble();
        double probabilityOfFailure = result.stream().mapToDouble(Pair::getValue).average().getAsDouble();

        System.out.println(averageSize + " -  average size of queue");
        System.out.println(probabilityOfFailure + "% - probability of failure");
    }

    public ArrayList<Pair> startExperiment(
            Integer times, Integer queueSize, Integer valuesToProduce, Integer consumers) {

        ArrayList<Pair> expetiments = new ArrayList<>();
        ArrayList<ExperimentInfo> experimentInfos = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < times; i++) {
            ExperimentInfo experimentInfo = new ExperimentInfo();
            experimentInfos.add(experimentInfo);
            threads.add(new Thread(() -> expetiments.add(startSMO(queueSize, valuesToProduce, consumers, experimentInfo))));
            threads.get(i).start();
        }

        boolean isAliveThreads = true;

        while (isAliveThreads) {
            System.out.print("\033[H\033[2J");
            TableBuilder tableBuilder = new TableBuilder();
            tableBuilder.addRow("alive consumers|", "alive producers|", "fails|", "queue length");
            experimentInfos.forEach(ei -> tableBuilder.addRow(
                    ei.getAliveConcumers() + "",
                    ei.getAliveProducers() + "",
                    ei.getFailers() + "",
                    ei.getQueueLength() + ""));
            System.out.println(tableBuilder.toString());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isAliveThreads = threads.stream().anyMatch(Thread::isAlive);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        expetiments.forEach(p -> {
            System.out.println("averageQueue : " + p.getKey());
            System.out.println("probability : " + p.getValue() + "%");
            System.out.println();
        });
        return expetiments;
    }

    public Pair startSMO(Integer queueSize, Integer valuesToProduce, Integer consumers, ExperimentInfo experimentInfo) {

        CubbyHole cubbyHole = new CubbyHole(queueSize);
        ArrayList<Thread> threads = new ArrayList<>();
        Double[] probability = new Double[1];
        Double[] averageQueue = new Double[1];

        threads.add(new Thread(new Producer(cubbyHole, valuesToProduce), "producer"));
        for (int i = 0; i < consumers; i++) {
            threads.add(new Thread(new Consumer(cubbyHole), "consumer" + i));
        }
        threads.forEach(Thread::start);

        Thread monitoringThread = new Thread(() -> {
            ArrayList<Integer> queueLengths = new ArrayList<>();
            boolean present = true;

            while (present) {
                queueLengths.add(cubbyHole.getSize());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                experimentInfo.setFailers(cubbyHole.getFailures());
                experimentInfo.setQueueLength(cubbyHole.getSize());
                Long aliveConsumers = threads.stream().filter(th -> th.isAlive() && th.getName().contains("consumer")).count();
                experimentInfo.setAliveConcumers(aliveConsumers.intValue());
                Long aliveProducers = threads.stream().filter(th -> th.isAlive() && th.getName().contains("producer")).count();
                experimentInfo.setAliveProducers(aliveProducers.intValue());

                present = threads.stream().anyMatch(Thread::isAlive);
            }
            probability[0] = cubbyHole.getFailures().doubleValue() / valuesToProduce * 100;
            averageQueue[0] = queueLengths.stream().mapToDouble(a -> a).average().getAsDouble();
        });
        monitoringThread.start();
        try {
            monitoringThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Pair(averageQueue[0], probability[0]);
    }

}
