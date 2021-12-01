package me.reszkojr.workshopjavafxjdbc.controllers;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.reszkojr.workshopjavafxjdbc.Main;
import me.reszkojr.workshopjavafxjdbc.db.DbIntegrityException;
import me.reszkojr.workshopjavafxjdbc.listeners.DataChangeListener;
import me.reszkojr.workshopjavafxjdbc.model.entities.Department;
import me.reszkojr.workshopjavafxjdbc.model.services.DepartmentService;
import utils.Alerts;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable, DataChangeListener {

    private DepartmentService service;

    @FXML
    private TableView<Department> tableViewDepartment;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, Department> tableColumnEDIT;

    @FXML
    private TableColumn<Department, Department> tableColumnDELETE;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btNew;

    private ObservableList<Department> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event) {

        // In case the button is pressed, it will instantiate a dialog form passing the current stage as a parent for the dialog form
        Stage parentStage = Utils.currentStage(event);
        Department obj = new Department();
        createDialogForm(obj, "gui/DepartmentForm.fxml", parentStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        // Makes the tableViewDepartment the same height as the main scene
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }

    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    public void updateTableView() {
        if (service == null)
            throw new IllegalStateException("Service is null");

        // Gets the list from DepartmentService to inject the items from it into the table
        List<Department> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewDepartment.setItems(obsList);
        initEditButtons();
        initDeleteButtons();
    }

    // Creates a dialog form from the arguments
    private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
        try {
            // Loads an FXML from absoluteName string
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(absoluteName));
            Pane pane = loader.load();

            // Gets the DepartmentFormController from the loader
            DepartmentFormController controller = loader.getController();
            controller.setDepartment(obj);  // Injects the dependency into it
            controller.updateFormData();
            controller.subscribeDataChangeListener(this); // Subscribes itself to receive a notification when the DepartmentFormController updates the database
            controller.setDepartmentService(new DepartmentService());

            // Creates a dialog stage, where it will be the stage whose gonna receive the updates from the database
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department data:");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL); // Makes the window modal, turning the main window unfocusable.
            dialogStage.showAndWait();

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void initDeleteButtons() {
        tableColumnDELETE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnDELETE.setCellFactory(param -> new TableCell<Department, Department>() {

            private final Button button = new Button("Delete");

            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    // Method that initiate the "Edit" buttons inside the "Edit" column.
    private void initEditButtons() {

        // Creates a CellValueFactory that is read-only.
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        // Instantiate a CellFactory with modified attributes.
        tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {

            // Creates the "Edit" button
            private final Button button = new Button("Edit");

            // Overrides the updateItem method to not update the items if the object is null
            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                // Sets the button into the column
                setGraphic(button);

                // If pressed, the button will open the dialog form with the object in the row
                button.setOnAction(event -> createDialogForm(obj, "gui/DepartmentForm.fxml", Utils.currentStage(event)));
            }

        });
    }

    private void removeEntity(Department obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

        if (result.get() == ButtonType.OK) {
            if (service == null) {
                throw new IllegalStateException("Service was null");
            }
            try {
                service.remove(obj);
                updateTableView();
            } catch (DbIntegrityException e) {
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @Override
    public void onDataChange() {
        updateTableView();
    }
}
