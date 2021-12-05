package utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class Utils {

    public static Stage currentStage(ActionEvent event) {

        // Gets the window that the event is in.
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

    public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String format) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Date> cell = new TableCell<T, Date>() {
                private SimpleDateFormat sdf = new SimpleDateFormat(format);

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        return;
                    }

                    setText(sdf.format(item));
                
                }
            };
            return cell;
        });
    }
    
    public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Double> cell = new TableCell<>() {

                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        return;
                    }

                    Locale.setDefault(Locale.US);
                    setText(String.format("%." + decimalPlaces + "f", item));

                }
            };

            return cell;
        });
    }
}
