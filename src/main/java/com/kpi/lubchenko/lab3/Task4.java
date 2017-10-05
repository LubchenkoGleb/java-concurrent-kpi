package com.kpi.lubchenko.lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Task4 {

    public static void main(String[] args) {
        int[][] matrix = Task4.generateMatrix(1000);
        long start = System.currentTimeMillis();
//        int[][] foxRes = Task4.calculateFox(matrix, matrix, 3);
        int[][] simpleRes = Task4.calculateSimple(matrix, matrix);
        long end = System.currentTimeMillis();

//        Task4.printMatrix(foxRes);
//        System.out.println();
//        Task4.printMatrix(simpleRes);
        System.out.println(end - start);
        System.out.println("first elem = " + simpleRes[0][0]);
    }

    public static int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 1;
//                matrix[i][j] = ((Double)(Math.random() * n)).intValue();
            }
        }
        return matrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] aMatrix : matrix) {
            for (int anAMatrix : aMatrix) {
                System.out.print(anAMatrix + " ");
            }
            System.out.println();
        }
    }

    public static int[][] calculateFox(int[][] a, int[][] b, int blockSize) {
        int n = a.length;
        int[][] c = new int[n][n];

        List<Thread> threads = new ArrayList<>();
        for (int counter = 0; counter < Math.pow(n / blockSize, 2); counter++) {
            int i = counter / (n / blockSize);
            int j = counter % (n / blockSize);

            threads.add(new Thread(() -> {
                for (int k = 0; k < blockSize; k++) {
                    for (int l = 0; l < blockSize; l++) {
                        for (int m = 0; m < n; m++) {
                            int aVal = a[i * blockSize + k][m];
                            int bVal = b[m][j * blockSize + l];
                            c[i * blockSize + k][j * blockSize + l] += aVal * bVal;
                        }
                    }
                }
            }));
        }
        threads.forEach(Thread::start);
        threads.forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return c;
    }

    public static int[][] calculateSimple(int[][] a, int[][] b) {
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
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return c;
    }


}
