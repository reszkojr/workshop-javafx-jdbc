package utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

    public static Stage currentStage(ActionEvent event) {

        // Gets the window that the event is in
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    // Tries to parse a certain string to int
    public static Integer tryParseToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e){
            return null;
        }
    }
}
