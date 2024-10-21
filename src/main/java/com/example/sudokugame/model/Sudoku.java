package com.example.sudokugame.model;

import java.util.*;

import java.util.Random;

public class Sudoku {
    ListAdapter<IList<Integer>> boardAdapter;
    ListAdapter<IList<Integer>> boardSolveSudoku;
    private Random random;

    public Sudoku() {
        boardAdapter = new ListAdapter<>(new LinkedList<>());
        random = new Random();

        // Crear el tablero 6x6 e inicializarlo con ceros
        for (int i = 0; i < 6; i++) {
            IList<Integer> row = new LinkedList<>();
            for (int j = 0; j < 6; j++) {
                row.addLast(0);
            }
            boardAdapter.getAdaptedList().addLast(row);
        }

        // Llenar los subcuadros 2x3 con dos números aleatorios válidos
        removeNumbersToCreatePuzzle();
    }

    // Método para generar un tablero de Sudoku solucionado utilizando backtracking
    public boolean solveSudoku() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (boardAdapter.getAdaptedList().get(row).get(col) == 0) { // Buscar un espacio vacío

                    // Crear una lista de números del 1 al 6 y desordenarla aleatoriamente
                    List<Integer> numbers = new ArrayList<>();
                    for (int num = 1; num <= 6; num++) {
                        numbers.add(num);
                    }
                    Collections.shuffle(numbers); // Mezclar los números

                    for (int num : numbers) { // Probar con los números aleatorios
                        if (isValid(row, col, num)) {
                            boardAdapter.getAdaptedList().get(row).set(col, num); // Colocar número provisionalmente

                            // Intentar resolver el resto del tablero
                            if (solveSudoku()) {
                                return true;
                            }

                            // Si la solución no es válida, se deshace el movimiento
                            boardAdapter.getAdaptedList().get(row).set(col, 0);
                        }
                    }
                    return false; // Si ningún número es válido, retrocede
                }
            }
        }
        boardSolveSudoku = cloneResolvedBoard();

        return true; // El tablero está completamente lleno y válido
    }

    // Método para clonar el tablero resuelto
    public ListAdapter<IList<Integer>> cloneResolvedBoard() {
        // Crear un nuevo ListAdapter con una copia profunda de cada fila
        ListAdapter<IList<Integer>> clonedBoard = new ListAdapter<>(new LinkedList<>());

        for (IList<Integer> row : boardAdapter.getAdaptedList()) {
            IList<Integer> clonedRow = new LinkedList<>();
            for (Integer value : row) {
                clonedRow.addLast(Integer.valueOf(value)); // Crear una copia independiente del valor
            }
            clonedBoard.getAdaptedList().addLast(clonedRow); // Añadir la fila copiada
        }

        return clonedBoard; // Devolver el tablero clonado
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
                    boardAdapter.getAdaptedList().get(pos[0]).set(pos[1], 0); // Eliminar el número de esta posición
                }
            }
        }



        System.out.println("___________________");
        for (int i = 0; i <6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(boardSolveSudoku.getAdaptedList().get(i).get(j));

            }
            System.out.println();
        }
        System.out.println("___________________");
        printBoard();
    }

    // Método para imprimir el tablero
    public void printBoard() {
        for (int i = 0; i < 6; i++) {
            IList<Integer> row = boardAdapter.getAdaptedList().get(i);
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
        boardAdapter.getAdaptedList().get(row).set(col, value);
    }

    // Método para asignar un valor a una posición en el tablero
    public void deletedValue(int row, int col) {
        if (row < 0 || row >= 6 || col < 0 || col >= 6) {
            throw new IllegalArgumentException("Posición o valor fuera de rango");
        }
        boardAdapter.getAdaptedList().get(row).set(col, 0);
    }

    public int getValue(int row, int col) {
        if (row < 0 || row >= 6 || col < 0 || col >= 6) {
            throw new IllegalArgumentException("Posición o valor fuera de rango");
        }

        return boardAdapter.getAdaptedList().get(row).get(col);
    }

    // Método para verificar si un valor es válido en una fila, columna y subcuadro
    public boolean isValid(int row, int col, int value) {
        // Verificar fila
        for (int j = 0; j < 6; j++) {
            if (boardAdapter.getAdaptedList().get(row).get(j) == value) {
                return false;
            }
        }

        // Verificar columna
        for (int i = 0; i < 6; i++) {
            if (boardAdapter.getAdaptedList().get(i).get(col) == value) {
                return false;
            }
        }

        // Verificar subcuadro 2x3
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (boardAdapter.getAdaptedList().get(i).get(j) == value) {
                    return false;
                }
            }
        }

        return true;
    }

    // Método para validar si el tablero de Sudoku está completo y no tiene números repetidos en filas y columnas
    public boolean isCompleteAndValid() {
        // Verificar filas
        for (int row = 0; row < 6; row++) {
            Set<Integer> seen = new HashSet<>();
            for (int col = 0; col < 6; col++) {
                int value = boardAdapter.getAdaptedList().get(row).get(col);
                if (value != 0) { // Ignorar los espacios vacíos
                    if (seen.contains(value)) {
                        return false; // Hay un número repetido en la fila
                    }
                    seen.add(value);
                }
            }
        }


        // Verificar columnas
        for (int col = 0; col < 6; col++) {
            Set<Integer> seen = new HashSet<>();
            for (int row = 0; row < 6; row++) {
                int value = boardAdapter.getAdaptedList().get(row).get(col);
                if (value != 0) { // Ignorar los espacios vacíos
                    if (seen.contains(value)) {
                        return false; // Hay un número repetido en la columna
                    }
                    seen.add(value);
                }
            }
        }

        return true; // Todas las filas y columnas son válidas
    }

    // Método para verificar si hay números repetidos en cada subcuadro 2x3
    public boolean isValidInSubGrids() {
        for (int startRow = 0; startRow < 6; startRow += 2) {
            for (int startCol = 0; startCol < 6; startCol += 3) {
                Set<Integer> seen = new HashSet<>();
                // Verificar cada subcuadro 2x3
                for (int i = startRow; i < startRow + 2; i++) {
                    for (int j = startCol; j < startCol + 3; j++) {
                        int value = boardAdapter.getAdaptedList().get(i).get(j);
                        if (value != 0) { // Ignorar los espacios vacíos
                            if (seen.contains(value)) {
                                return false; // Hay un número repetido en el subcuadro
                            }
                            seen.add(value);
                        }
                    }
                }
            }
        }
        return true; // Todos los subcuadros son válidos
    }

    // Método para validar si el tablero de Sudoku está completo y no tiene números repetidos en filas, columnas y subcuadros
    public boolean isCompleteAndValidWithSubGrids() {
        System.out.println("isCompleteAndValid: " + isCompleteAndValid() + " -  isValidInSubGrids(): " + isValidInSubGrids());
        return isCompleteAndValid() && isValidInSubGrids();
    }


    // Método para borrar el tablero
    public void clearBoard() {
        for (int i = 0; i < 6; i++) {
            IList<Integer> row = boardAdapter.getAdaptedList().get(i);
            for (int j = 0; j < 6; j++) {
                row.set(j, 0);
            }
        }
    }

    public int[] fillRandomCell() {
        List<int[]> emptyCells = new ArrayList<>();

        // Recolectar todas las celdas vacías en boardAdapter
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (boardAdapter.getAdaptedList().get(row).get(col) == 0) { // Si la celda está vacía
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        if (emptyCells.isEmpty()) {
            return null; // No hay celdas vacías
        }

        // Seleccionar una celda vacía aleatoriamente
        int[] randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
        int row = randomCell[0];
        int col = randomCell[1];

        // Obtener el valor correspondiente de boardSolveSudoku
        int solvedValue = boardSolveSudoku.getAdaptedList().get(row).get(col);

        // Colocar el valor en boardAdapter
        boardAdapter.getAdaptedList().get(row).set(col, solvedValue);

        return new int[]{row, col}; // Devolver la celda actualizada
    }

    public boolean checkWin() {
        // Verifica si ambos tableros tienen el mismo tamaño
        if (boardAdapter.getAdaptedList().size() != boardSolveSudoku.getAdaptedList().size()) {
            return false;
        }

        // Iterar sobre todas las filas del tablero
        for (int row = 0; row < boardAdapter.getAdaptedList().size(); row++) {
            IList<Integer> currentRow = boardAdapter.getAdaptedList().get(row);
            IList<Integer> solutionRow = boardSolveSudoku.getAdaptedList().get(row);

            // Verificar si el tamaño de la fila actual coincide
            if (currentRow.size() != solutionRow.size()) {
                return false;
            }

            // Comparar cada elemento (número) en la fila actual
            for (int col = 0; col < currentRow.size(); col++) {
                if (!currentRow.get(col).equals(solutionRow.get(col))) {
                    return false;  // Si algún número no coincide, no has ganado
                }
            }
        }

        // Si todos los elementos coinciden, el jugador ha ganado
        return true;
    }

}


