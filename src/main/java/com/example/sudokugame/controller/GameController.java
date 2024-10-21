package com.example.sudokugame.controller;

import com.example.sudokugame.model.AlertBox;
import com.example.sudokugame.model.Sudoku;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class GameController {

    private int helpCounter = 0;  // Contador de uso del botón de ayuda
    private final int MAX_HELP_USES = 3;


    @FXML
    private Button helpButton;

    @FXML
    private GridPane gridPane;
    Sudoku sudoku = new Sudoku();

    @FXML
    void onHandleButtonHelp(ActionEvent event) {

        if (helpCounter < MAX_HELP_USES) {
            int[] updatedCell = sudoku.fillRandomCell();  // Actualiza una celda aleatoria

            if (updatedCell != null) {
                resetGridPane();  // Borra y vuelve a crear la cuadrícula con las celdas actualizadas
                helpCounter++;  // Incrementar el contador de uso de la ayuda
                //new AlertBox().showMessageInformation("Has usado la ayuda " + helpCounter + " de " + MAX_HELP_USES + " veces.");
            } else {
                new AlertBox().showMessageInformation("No hay más celdas vacías o no se puede encontrar una solución válida.");
            }

            // Deshabilitar el botón si se alcanza el límite de usos
            if (helpCounter >= MAX_HELP_USES) {
                helpButton.setDisable(true);
                new AlertBox().showMessageInformation("Ya no puedes usar más ayudas.");
            }
        } else {
            new AlertBox().showMessageInformation("Ya no puedes usar más ayudas.");
        }

        if(sudoku.checkWin()){
            new AlertBox().showMessageInformation("¡Felicidades! Has completado el Sudoku correctamente.");
        }

    }



    // Método para borrar y volver a crear el GridPane según el estado actual del Sudoku
    private void resetGridPane() {
        // Limpiar el GridPane
        gridPane.getChildren().clear();

        // Reconstruir el GridPane basado en los valores actuales del Sudoku
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField txt = new TextField();
                textFieldStyle(txt); // Aplicar estilo al TextField
                txt.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, new BorderWidths(1))));

                int value = sudoku.getValue(i, j); // Obtener el valor del Sudoku

                if (value != 0) {
                    txt.setText(String.valueOf(value)); // Establecer el valor si no es 0
                }

                verityEmptyNumber(txt, value); // Verificar si la celda está vacía o tiene un número
                gridPane.add(txt, j, i); // Agregar el TextField al GridPane en la posición correcta
                onKeyTxtPressed(txt, i, j); // Escuchar los cambios de las teclas en la celda
            }
        }
    }


    private void onKeyTxtPressed(TextField txt, int i, int j) {
        txt.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty()) {
                txt.setStyle("-fx-background-color: #b693ff;");
                return change;
            }

            if (!newText.matches("[1-6]")) {
                txt.setStyle("-fx-background-color: #b693ff; -fx-border-color: red; -fx-border-width: 2px;");
                String content = "Solo se puedes ingresar números entre '1' y '6'";
                new AlertBox().showMessageInformation(content);
                return null;
            } else {
                // Si la entrada es válida, restaurar el borde a su estado normal
                txt.setStyle("-fx-background-color: #b693ff;");
            }

            return change;
        }));

        txt.setOnKeyReleased(keyEvent -> {
            String input = txt.getText().trim();
            if (input.isEmpty()) return;

            int number = Integer.parseInt(input); // Convertir el carácter a número

            sudoku.setValue(i, j, number);  // Guardar la entrada del jugador

            if (!sudoku.isCompleteAndValidWithSubGrids()) {
                txt.setStyle("-fx-background-color: #b693ff; -fx-border-color: red; -fx-border-width: 2px;");
                String content = "No se puede ingresar el número (" + input + ") porque está en una columna, fila o subcuadro";
                new AlertBox().showMessageInformation(content);
                sudoku.deletedValue(i, j); // Eliminar el valor si no es válido
                txt.clear();
            }

            if(sudoku.checkWin()){
                new AlertBox().showMessageInformation("¡Felicidades! Has completado el Sudoku correctamente.");
            }
        });


    }

    // Método auxiliar para aplicar el estilo a un TextField
    private void verityEmptyNumber(TextField txt, int n) {
        if (n != 0) {
            txt.setEditable(false);
            txt.setBackground(new Background(new BackgroundFill(Color.rgb(240, 240, 240), null, null)));
        } else {
            txt.clear();
            txt.setEditable(true);
            txt.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
        }
    }

    // Estilo del TextField
    public TextField textFieldStyle(TextField txt) {
        txt.setMaxWidth(50);
        txt.setMaxHeight(50);
        txt.setFont(new Font("Verdana", 20));
        txt.setAlignment(Pos.CENTER);
        txt.setStyle("-fx-background-color: #b693ff; -fx-text-fill: black;");
        return txt;
    }

    public void initialize() {
        // Inicializa el GridPane al inicio
        resetGridPane();
    }
}

