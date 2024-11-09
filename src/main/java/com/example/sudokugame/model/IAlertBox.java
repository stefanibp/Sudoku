package com.example.sudokugame.model;
/**
 * The IAlertBox interface defines a contract for displaying alert messages.
 * Classes implementing this interface should provide functionality for showing
 * messages with a title, header, and content.
 */
public interface IAlertBox {
    /**
     * Displays an alert message with the specified title, header, and content.
     *
     * @param title   The title of the alert window.
     * @param header  The header text of the alert.
     * @param content The content of the alert message.
     */
    void showMessage(String title, String header, String content);
}
