package com.example.sudokugame.model;

import java.util.*;

import java.util.Random;

public class Sudoku {
    private IList<IList<Integer>> board;
    private Random random;

    public Sudoku() {
        board = new LinkedList<>();
        random = new Random();

        // Crear el tablero 6x6 e inicializarlo con ceros
        for (int i = 0; i < 6; i++) {
            IList<Integer> row = new LinkedList<>();
            for (int j = 0; j < 6; j++) {
                row.addLast(0);
            }
            board.addLast(row);
        }

        // Llenar los subcuadros 2x3 con dos números aleatorios válidos
        removeNumbersToCreatePuzzle();
    }

    // Método para generar un tablero de Sudoku solucionado utilizando backtracking
    public boolean solveSudoku() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (board.get(row).get(col) == 0) { // Buscar un espacio vacío

                    // Crear una lista de números del 1 al 6 y desordenarla aleatoriamente
                    List<Integer> numbers = new ArrayList<>();
                    for (int num = 1; num <= 6; num++) {
                        numbers.add(num);
                    }
                    Collections.shuffle(numbers); // Mezclar los números

                    for (int num : numbers) { // Probar con los números aleatorios
                        if (isValid(row, col, num)) {
                            board.get(row).set(col, num); // Colocar número provisionalmente

                            // Intentar resolver el resto del tablero
                            if (solveSudoku()) {
                                return true;
                            }

                            // Si la solución no es válida, se deshace el movimiento
                            board.get(row).set(col, 0);
                        }
                    }
                    return false; // Si ningún número es válido, retrocede
                }
            }
        }

        /*
        this.printBoard();

        System.out.println("");
        System.out.println("_______________________");
        System.out.println("");

        */
        return true; // El tablero está completamente lleno y válido
    }


    // Método para eliminar números aleatoriamente del tablero, dejando exactamente dos por subcuadro 2x3
    public void removeNumbersToCreatePuzzle() {
        // Primero generamos el tablero completamente solucionado
        if (!solveSudoku()) {
            System.out.println("No se pudo generar un tablero solucionado.");
            return;
        }

        // Eliminar números aleatoriamente, asegurando que queden exactamente dos números por subcuadro
        Random random = new Random();

        for (int subGridRow = 0; subGridRow < 6; subGridRow += 2) {
            for (int subGridCol = 0; subGridCol < 6; subGridCol += 3) {
                // Obtener todas las posiciones dentro del subcuadro 2x3
                List<int[]> positions = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        positions.add(new int[] {subGridRow + i, subGridCol + j});
                    }
                }

                // Eliminar todos los números excepto dos aleatoriamente
                while (positions.size() > 2) {
                    int index = random.nextInt(positions.size());
                    int[] pos = positions.remove(index);
                    board.get(pos[0]).set(pos[1], 0); // Eliminar el número de esta posición
                }
            }
        }
    }

    // Método para imprimir el tablero
    public void printBoard() {
        for (int i = 0; i < 6; i++) {
            IList<Integer> row = board.get(i);
            for (int j = 0; j < 6; j++) {
                System.out.print(row.get(j) + " ");
            }
            System.out.println();
        }
    }

    // Método para asignar un valor a una posición en el tablero
    public void setValue(int row, int col, int value) {
        if (row < 0 || row >= 6 || col < 0 || col >= 6 || value < 1 || value > 6) {
            throw new IllegalArgumentException("Posición o valor fuera de rango");
        }
        board.get(row).set(col, value);
    }

    public int getValue(int row, int col) {
        if (row < 0 || row >= 6 || col < 0 || col >= 6) {
            throw new IllegalArgumentException("Posición o valor fuera de rango");
        }

        return board.get(row).get(col);
    }

    // Método para verificar si un valor es válido en una fila, columna y subcuadro
    public boolean isValid(int row, int col, int value) {
        // Verificar fila
        for (int j = 0; j < 6; j++) {
            if (board.get(row).get(j) == value) {
                return false;
            }
        }

        // Verificar columna
        for (int i = 0; i < 6; i++) {
            if (board.get(i).get(col) == value) {
                return false;
            }
        }

        // Verificar subcuadro 2x3
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board.get(i).get(j) == value) {
                    return false;
                }
            }
        }

        return true;
    }

    // Método para borrar el tablero
    public void clearBoard() {
        for (int i = 0; i < 6; i++) {
            IList<Integer> row = board.get(i);
            for (int j = 0; j < 6; j++) {
                row.set(j, 0);
            }
        }
    }
}


