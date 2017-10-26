package com.kpi.lubchenko.lab4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lab4 {

    public static void main(String[] args) throws InterruptedException {

//        int size = 100;
//        int[][] aRand = MatrixUtil.generateRandomMatrix(size);
//        int[][] bRand = MatrixUtil.generateRandomMatrix(size);
//
//        int[][] resSimp = calculateSimple(aRand, bRand);
//        MatrixUtil.printFragment(resSimp, 3);
//
//        int[][] resCannon1 = calculateCanon(aRand, bRand, 1);
//        MatrixUtil.printFragment(resCannon1, 3);
//
//        int[][] resCannon2 = calculateCanon(aRand, bRand, 10);
//        MatrixUtil.printFragment(resCannon2, 3);
//
//        int[][] resCannon3 = calculateCanon(aRand, bRand, 100);
//        MatrixUtil.printFragment(resCannon3, 3);


        for (int i = 1; i <= 10; i++) {
            int size = i * 100;
            int[][] aRand = MatrixUtil.generateRandomMatrix(size);
            int[][] bRand = MatrixUtil.generateRandomMatrix(size);
            makeExperiments(aRand, bRand, size);
        }

        for (int i = 1; i <= 10; i++) {
            int size = i * 100;
            int[][] aSingle = MatrixUtil.generateSingleMatrix(size);
            int[][] bSingle = aSingle;
            makeExperiments(aSingle, bSingle, size);
        }
    }

    public static void makeExperiments(int[][] a, int[][] b, int size) throws InterruptedException {

        Experiment experiment1 = new Experiment("Fox, size=" + size + ", blockSize=" + size);
        int[][] experiment1Result = calculateFox(a, b, size);
        experiment1.setEndTime();
        MatrixUtil.printFragment(experiment1Result, 3);

        Experiment experiment2 = new Experiment("Fox, size=" + size + ", blockSize=" + size / 2);
        int[][] experiment2Result = calculateFox(a, b, size / 2);
        experiment2.setEndTime();
        MatrixUtil.printFragment(experiment2Result, 3);

        Experiment experiment3 = new Experiment("Fox, size=" + size + ", blockSize=" + 1);
        int[][] experiment3Result = calculateFox(a, b, 1);
        experiment3.setEndTime();
        MatrixUtil.printFragment(experiment3Result, 3);

        Experiment experiment4 = new Experiment("Canon, size=" + size + ", blockSize=" + size);
        int[][] experiment4Result = calculateCanon(a, b, size);
        experiment4.setEndTime();
        MatrixUtil.printFragment(experiment4Result, 3);

        Experiment experiment5 = new Experiment("Canon, size=" + size + ", blockSize=" + size / 2);
        int[][] experiment5Result = calculateCanon(a, b, size / 2);
        experiment5.setEndTime();
        MatrixUtil.printFragment(experiment5Result, 3);

        Experiment experiment6 = new Experiment("Canon, size=" + size + ", blockSize=" + 1);
        int[][] experiment6Result = calculateCanon(a, b, 1);
        experiment6.setEndTime();
        MatrixUtil.printFragment(experiment6Result, 3);

        Experiment experiment7 = new Experiment("Simple, size=" + size + "'");
        int[][] experiment7Result = calculateSimple(a, b);
        experiment7.setEndTime();
        MatrixUtil.printFragment(experiment7Result, 3);

        Experiment.compareExperiments(experiment1, experiment2,
                experiment3, experiment4, experiment5, experiment6, experiment7);
    }

    public static int[][] calculateFox(int[][] a, int[][] b, int blockSize) throws InterruptedException {
        int n = a.length;
        int[][] c = new int[n][n];

        ExecutorService executorService = Executors.newWorkStealingPool();
        for (int counter = 0; counter < Math.pow(n / blockSize, 2); counter++) {
            int i = counter / (n / blockSize);
            int j = counter % (n / blockSize);

            executorService.submit(() -> {
                for (int k = 0; k < blockSize; k++) {
                    for (int l = 0; l < blockSize; l++) {
                        for (int m = 0; m < n; m++) {
                            int aVal = a[i * blockSize + k][m];
                            int bVal = b[m][j * blockSize + l];
                            c[i * blockSize + k][j * blockSize + l] += aVal * bVal;
                        }
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        return c;
    }

    public static int[][] calculateCanon(int[][] a, int[][] b, int blockSize) throws InterruptedException {
        int n = a.length;
        int[][] c = new int[n][n];

        ExecutorService executorService = Executors.newWorkStealingPool();
        for (int i = 0; i < n; i += blockSize) {
            for (int j = 0; j < n; j += blockSize) {
                int row = i;
                int col = j;

                executorService.submit(() -> {
                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {
                            for (int m = 0; m < n; m++) {
                                int aVal = a[row + k][m];
                                int bVal = b[m][col + l];
                                c[row + k][col + l] += aVal * bVal;
                            }
                        }
                    }
                });
            }
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        return c;
    }

    public static int[][] calculateSimple(int[][] a, int[][] b) throws InterruptedException {
        int n = a.length;
        int[][] c = new int[n][n];

        ExecutorService executorService = Executors.newWorkStealingPool();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                int row = i;
                int col = j;
                executorService.submit(() -> {
                    for (int k = 0; k < n; k++) {
                        c[row][col] += a[row][k] * b[k][col];
                    }
                });
            }
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        return c;
    }
}
