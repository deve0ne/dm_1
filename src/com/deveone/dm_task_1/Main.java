package com.deveone.dm_task_1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[][] matrix = readMatrixFromFile("input.txt");

        if (matrix.length == 0) {
            System.err.println("\nMatrix is empty :|");
            return;
        }

        int determinant = getDeterminant(matrix, matrix.length);

        writeAnswerToFile("output.txt", determinant);
    }

    static int getDeterminant(int[][] matrix, int n) {
        int determinant = 0;

        if (n == 1)
            return matrix[0][0];

        if (n == 2)
            return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];

        int[][] tmp = new int[n][n];

        for (int f = 0; f < n; f++) {
            int i = 0;
            int j = 0;

            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (row != 0 && col != f) {
                        tmp[i][j++] = matrix[row][col];

                        if (j == n - 1) {
                            j = 0;
                            i++;
                        }
                    }
                }
            }

            determinant += Math.pow(-1, f) * matrix[0][f] * getDeterminant(tmp, n - 1);
        }

        return determinant;
    }

    private static int[][] readMatrixFromFile(String filepath) {
        File file = new File(filepath);
        List<String> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);

            while (scanner.hasNext())
                lines.add(scanner.nextLine());

            scanner.close();
        } catch (IOException e) {
            System.err.println("Error on reading from file :(");
            e.printStackTrace();
            return new int[0][0];
        }

        return parseMatrix(lines.toArray(new String[0]));
    }

    private static int[][] parseMatrix(String[] lines) {
        int[][] matrix = new int[lines.length][];

        for (int row = 0; row < matrix.length; row++)
            matrix[row] = parseMatrixRow(lines[row]);

        return matrix;
    }

    private static int[] parseMatrixRow(String row) {
        Scanner scanner = new Scanner(row);

        List<Integer> list = new ArrayList<>();

        while (scanner.hasNext())
            list.add(scanner.nextInt());

        return list.stream().mapToInt(i -> i).toArray();
    }

    private static void writeAnswerToFile(String filepath, int determinant) {
        File file = new File(filepath);

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.err.println("Error on creating file :(");
            e.printStackTrace();
            return;
        }

        try {
            FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);

            writer.write(String.valueOf(determinant));

            writer.close();
        } catch (IOException e) {
            System.err.println("Error on writing to file :(");
            e.printStackTrace();
        }
    }

}