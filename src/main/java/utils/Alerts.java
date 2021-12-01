package utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class Alerts {

    public static void showAlert(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(header);
        alert.setTitle(content);
        alert.show();
    }

    public static Optional<ButtonType> showConfirmation(String title, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setTitle(title);
        return alert.showAndWait();
    }
}
