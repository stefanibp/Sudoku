package com.example.sudokugame.model;

import javafx.scene.control.Alert;

public class AlertBox implements IAlertBox {

    @Override
    public void showMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void showMessageInformation(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Versión con el valor por defecto de `title`
    public void showMessageInformation(String header, String content) {
        showMessageInformation("Alerta", header, content);
    }

    // Versión con valores por defecto para `title` y `header`
    public void showMessageInformation(String content) {
        showMessageInformation("Alerta", "Información", content);
    }
}

