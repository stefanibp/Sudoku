package com.example.sudokugame.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeStage extends Stage {

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

}
