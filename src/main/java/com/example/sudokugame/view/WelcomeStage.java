package com.example.sudokugame.view;

import com.example.sudokugame.controller.WelcomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code WelcomeStage} class is responsible for creating and managing the welcome stage
 * of the Sudoku game. This class is a singleton to ensure that only one instance of the welcome
 * stage exists during the runtime of the application.
 */
public class WelcomeStage extends Stage {

    /**
     * Constructs a new {@code WelcomeStage}, loads the FXML file and sets the scene.
     *
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    public WelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokugame/welcome-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Bienvenido");
        setResizable(false);
        getIcons().add(new Image(getClass().getResourceAsStream("/com/example/sudokugame/icon.png")));
        show();
    }

    /**
     * Returns the singleton instance of {@code WelcomeStage}. If the instance does not already exist,
     * it creates a new one.
     *
     * @return The singleton instance of {@code WelcomeStage}.
     * @throws IOException If an I/O error occurs while creating the instance.
     */
    public static WelcomeStage getInstance() throws IOException{
        WelcomeStageHolder.INSTANCE =
                WelcomeStageHolder.INSTANCE != null ?
                        WelcomeStageHolder.INSTANCE :
                        new WelcomeStage();

        return WelcomeStageHolder.INSTANCE;
    }

    /**
     * Inner static class used for holding the singleton instance of {@code WelcomeStage}.
     */
    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE;
    }

    /**
     * Deletes the singleton instance of {@code WelcomeStage}, closing the current stage and setting
     * the instance to {@code null}.
     */
    public static void deleteInstance() {
        WelcomeStageHolder.INSTANCE.close();
        WelcomeStageHolder.INSTANCE = null;
    }

}
