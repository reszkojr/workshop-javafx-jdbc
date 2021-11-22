package me.reszkojr.workshopjavafxjdbc.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.reszkojr.workshopjavafxjdbc.Main;
import me.reszkojr.workshopjavafxjdbc.model.entities.Department;
import me.reszkojr.workshopjavafxjdbc.model.services.DepartmentService;
import utils.Alerts;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {

    private DepartmentService service;

    @FXML
    private TableView<Department> tableViewDepartment;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btNew;

    private ObservableList<Department> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event) {
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
    }

    private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(absoluteName));
            Pane pane = loader.load();

            DepartmentFormController controller = loader.getController();
            controller.setDepartment(obj);
            controller.updateFormData();
            controller.setDepartmentService(new DepartmentService());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department data:");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
