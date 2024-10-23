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

/**
 * This class controls the interaction between the Sudoku game and the user interface.
 * It manages the game's logic, user input, and grid updates, allowing users to play Sudoku interactively.
 *
 * @author Stefania Bolaños
 */
public class GameController {

    /**
     * Counter for the number of times the user has requested help.
     */
    private int helpCounter = 0;
    /**
     * Maximum number of help uses allowed per game.
     */
    private final int MAX_HELP_USES = 3;


    @FXML
    private Button helpButton;

    @FXML
    private GridPane gridPane;

    /**
     * Sudoku game model.
     */
    Sudoku sudoku = new Sudoku();

    /**
     * Handles the help button click event. Fills a random empty cell with the correct value.
     *
     * @param event The ActionEvent triggered by the help button.
     */
    @FXML
    void onHandleButtonHelp(ActionEvent event) {

        if (helpCounter < MAX_HELP_USES) {
            int[] updatedCell = sudoku.fillRandomCell();

            if (updatedCell != null) {
                resetGridPane();
                helpCounter++;

            } else {
                new AlertBox().showMessageInformation("No hay más celdas vacías o no se puede encontrar una solución válida.");
            }


            if (helpCounter > MAX_HELP_USES) {
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

    /**
     * Resets the GridPane by clearing and re-populating it with the current Sudoku board values.
     */
    private void resetGridPane() {

        gridPane.getChildren().clear();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField txt = new TextField();
                textFieldStyle(txt);
                txt.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, new BorderWidths(1))));

                int value = sudoku.getValue(i, j);

                if (value != 0) {
                    txt.setText(String.valueOf(value));
                }

                verityEmptyNumber(txt, value);
                gridPane.add(txt, j, i);
                onKeyTxtPressed(txt, i, j);
            }
        }
    }

    /**
     * Adds a listener to the TextField to handle key press events.
     * Validates user input and updates the Sudoku board accordingly.
     *
     * @param txt The TextField representing a cell in the Sudoku grid.
     * @param i   The row index of the cell.
     * @param j   The column index of the cell.
     */
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

                txt.setStyle("-fx-background-color: #b693ff;");
            }

            return change;
        }));

        txt.setOnKeyReleased(keyEvent -> {
            String input = txt.getText().trim();
            if (input.isEmpty()) return;

            int number = Integer.parseInt(input);

            sudoku.setValue(i, j, number);

            if (!sudoku.isCompleteAndValidWithSubGrids()) {
                txt.setStyle("-fx-background-color: #b693ff; -fx-border-color: red; -fx-border-width: 2px;");
                String content = "No se puede ingresar el número (" + input + ") porque está en una columna, fila o subcuadro";
                new AlertBox().showMessageInformation(content);
                sudoku.deletedValue(i, j);
                txt.clear();
            }

            if(sudoku.checkWin()){
                new AlertBox().showMessageInformation("¡Felicidades! Has completado el Sudoku correctamente.");
            }
        });

    }

    /**
     * Updates the text field appearance based on whether it contains a number or is empty.
     * If the field contains a number, it becomes non-editable, and if it's empty, it allows input.
     *
     * @param txt The text field to verify.
     * @param n The number in the text field.
     */
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

    /**
     * Applies a specific style to the given text field, including font, size, and alignment.
     *
     * @param txt The text field to style.
     * @return The styled text field.
     */
    public TextField textFieldStyle(TextField txt) {
        txt.setMaxWidth(50);
        txt.setMaxHeight(50);
        txt.setFont(new Font("Verdana", 20));
        txt.setAlignment(Pos.CENTER);
        txt.setStyle("-fx-background-color: #b693ff; -fx-text-fill: black;");
        return txt;
    }

    /**
     * Initializes the game by setting up the Sudoku grid pane when the application starts.
     * This method is automatically called after the FXML fields are injected.
     */
    public void initialize() {

        resetGridPane();
    }
}

