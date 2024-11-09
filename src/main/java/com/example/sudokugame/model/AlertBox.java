package com.example.sudokugame.model;

import javafx.scene.control.Alert;

/**
 * AlertBox provides methods to display different types of alert messages
 * using JavaFX's {@link Alert} class. It allows for error messages and informational messages
 * to be displayed with varying levels of detail.
 */
public class AlertBox implements IAlertBox {

    /**
     * Displays an error message using an {@link Alert} of type ERROR.
     *
     * @param title   The title of the alert window.
     * @param header  The header text of the alert.
     * @param content The content of the alert message.
     */
    @Override
    public void showMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    /**
     * Displays an informational message using an {@link Alert} of type INFORMATION.
     *
     * @param title   The title of the alert window.
     * @param header  The header text of the alert.
     * @param content The content of the alert message.
     */
    public void showMessageInformation(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showMessageInformation(String header, String content) {
        showMessageInformation("Alerta", header, content);
    }

    /**
     * Displays an informational message using default title "Alerta" and header "Información".
     *
     * @param content The content of the alert message.
     */
    public void showMessageInformation(String content) {
        showMessageInformation("Alerta", "Información", content);
    }
}

