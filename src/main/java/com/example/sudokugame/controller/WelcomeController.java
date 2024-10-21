package com.example.sudokugame.controller;

import com.example.sudokugame.view.GameStage;
import com.example.sudokugame.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class WelcomeController {


    @FXML
    void buttonPlay(ActionEvent event) throws IOException {
        /** When the "Start" button is clicked initialize the GameController for the game */
        GameStage.getInstance().getGameController().initialize();
        /** Close the WelcomeStage */
        WelcomeStage.deleteInstance();

    }

}
