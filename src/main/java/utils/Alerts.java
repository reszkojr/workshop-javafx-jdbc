package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;

public class Alerts {

    public static void showAlert(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(header);
        alert.setTitle(content);
        alert.show();
    }
}
