package com.example.sudokugame.view;

import com.example.sudokugame.controller.WelcomeController;
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


    public static WelcomeStage getInstance() throws IOException{
        WelcomeStageHolder.INSTANCE =
                WelcomeStageHolder.INSTANCE != null ?
                        WelcomeStageHolder.INSTANCE :
                        new WelcomeStage();

        return WelcomeStageHolder.INSTANCE;
    }

    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE;
    }

    public static void deleteInstance() {
        WelcomeStageHolder.INSTANCE.close();
        WelcomeStageHolder.INSTANCE = null;
    }

}
