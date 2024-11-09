package com.example.sudokugame;


import com.example.sudokugame.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main class serves as the entry point for the Sudoku game application.
 * It extends {@link Application} to launch the JavaFX application.
 */
public class Main extends Application {

    /**
     * The main method that serves as the entry point for the application.
     * It calls the {@link Application#launch(String...)} method to launch the JavaFX application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method is called after the JavaFX application is launched.
     * It initializes the primary stage and loads the initial scene.
     *
     * @param primaryStage The primary stage for this JavaFX application.
     * @throws IOException If an input/output error occurs during the initialization of the welcome stage.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        WelcomeStage.getInstance();
    }
}
