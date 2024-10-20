package com.example.sudokugame.view;

import com.example.sudokugame.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GameStage extends Stage {

        //private GameController gameController;


        public GameStage() throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokugame/game-view.fxml"));
            Parent root = loader.load();
            //gameController = loader.getController();
            Scene scene = new Scene(root);
            setScene(scene);
            setTitle("Sudoku");
            getIcons().add(new Image(getClass().getResourceAsStream("/com/example/sudokugame/icon.png")));
            setResizable(false);
            show();
        }
}
