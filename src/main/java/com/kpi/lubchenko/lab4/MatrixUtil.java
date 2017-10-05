package com.kpi.lubchenko.lab4;

public class MatrixUtil {

    public static int[][] generateRandomMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = ((Double)(Math.random() * size)).intValue();
            }
        }
        return matrix;
    }

    public static int[][] generateSingleMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 1;
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
        System.out.println("---------------------------------");
    }

    public static void printFragment(int[][] matrix, int fragmentSize) {
        for (int i = 0; i < fragmentSize; i++) {
            for (int j = 0; j < fragmentSize; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------");
    }
}
