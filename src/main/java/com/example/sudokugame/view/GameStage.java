package com.example.sudokugame.view;

import com.example.sudokugame.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code GameStage} class is responsible for creating and managing the game stage
 * of the Sudoku game. This class follows the Singleton pattern to ensure that only one
 * instance of the game stage exists during the runtime of the application.
 */
public class GameStage extends Stage {

        private GameController gameController;

    /**
     * Constructs a new {@code GameStage}, loads the FXML file, retrieves the game controller,
     * and sets up the scene and stage.
     *
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
        public GameStage() throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokugame/game-view.fxml"));
            Parent root = loader.load();
            gameController = loader.getController();
            Scene scene = new Scene(root);
            setScene(scene);
            setTitle("Sudoku");
            getIcons().add(new Image(getClass().getResourceAsStream("/com/example/sudokugame/icon.png")));
            setResizable(false);
            show();
        }

    /**
     * Returns the {@code GameController} associated with this stage.
     *
     * @return The instance of {@code GameController} managing the game logic.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Returns the singleton instance of {@code GameStage}. If the instance does not already exist,
     * it creates a new one.
     *
     * @return The singleton instance of {@code GameStage}.
     * @throws IOException If an I/O error occurs while creating the instance.
     */
    public static GameStage getInstance() throws IOException {
        GameStageHolder.INSTANCE =
                GameStageHolder.INSTANCE != null ?
                        GameStageHolder.INSTANCE :
                        new GameStage();

        return GameStageHolder.INSTANCE;
    }

    /**
     * Inner static class used for holding the singleton instance of {@code GameStage}.
     */
    private static class GameStageHolder {
        private static GameStage INSTANCE;
    }

    /**
     * Deletes the singleton instance of {@code GameStage}, closing the current stage and setting
     * the instance to {@code null}.
     */
    public static void deleteInstance() {
        GameStageHolder.INSTANCE.close();
        GameStageHolder.INSTANCE = null;
    }
}
