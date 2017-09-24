package com.kpi.lubchenko.lab3;

import java.util.ArrayList;
import java.util.List;

public class Task4 {

    int[][] a = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
    };

    int[][] b = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
    };

    public static void main(String[] args) {
        Task4 task4 = new Task4();
        int[][] foxRes = Task4.calculateFox(task4.a, task4.b);
        int[][] simpleRes = Task4.calculateSimple(task4.a, task4.b);

        Task4.printMatrix(foxRes);
        System.out.println();
        Task4.printMatrix(simpleRes);
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] aMatrix : matrix) {
            for (int anAMatrix : aMatrix) {
                System.out.print(anAMatrix + " ");
            }
            System.out.println();
        }
    }

    public static int[][] calculateFox(int[][] a, int[][] b) {
        int n = a.length;
        int[][] c = new int[n][n];

        List<Thread> threads = new ArrayList<>();
        for (int counter = 0; counter < Math.pow(n, 2); counter++) {
            int i = counter / n;
            int j = counter % n;

            threads.add(new Thread(() -> {
                for (int k = 0; k < n; k++) {
                    c[i][j] += a[i][k] * b[k][j];
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

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                int row = i;
                int col = j;
                threads.add(new Thread(() -> {
                    for (int k = 0; k < n; k++) {
                        c[row][col] += a[row][k] * b[k][col];
                    }
                }));
            }
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


}
