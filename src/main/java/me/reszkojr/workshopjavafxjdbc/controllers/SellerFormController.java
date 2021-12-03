package me.reszkojr.workshopjavafxjdbc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import me.reszkojr.workshopjavafxjdbc.db.DbException;
import me.reszkojr.workshopjavafxjdbc.listeners.DataChangeListener;
import me.reszkojr.workshopjavafxjdbc.model.entities.Seller;
import me.reszkojr.workshopjavafxjdbc.model.exceptions.ValidationException;
import me.reszkojr.workshopjavafxjdbc.model.services.SellerService;
import utils.Alerts;
import utils.Constraints;
import utils.Utils;

import java.net.URL;
import java.util.*;

public class SellerFormController implements Initializable {

    private Seller entity;
    private SellerService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label errorLabelName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Entity is null");
        }
        if (service == null) {
            throw new IllegalStateException("Service is null");
        }

        try {
            entity = getFormData(); // Gets the data that was written inside the form.
            service.saveOrUpdate(entity); // Saves the data inside the service and insert it into the database.
            notifyDataChangeListeners(); // Notifies all the DataChangeListeners to update the TableView.
            Utils.currentStage(event).close(); // Closes the currentStage, exiting the form.
        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChange();
        }
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    public void setSeller(Seller entity) {
        this.entity = entity;
    }

    public void setSellerService(SellerService service) {
        this.service = service;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        // Method used to subscribe a class into the DataChangeListener to do some action.
        dataChangeListeners.add(listener);
    }

    private Seller getFormData() {

        Seller obj = new Seller();
        ValidationException exception = new ValidationException("Validation error");

        obj.setId(Utils.tryParseToInt(txtId.getText())); // Gets the Id from the form.

        // Verifications to check if everything is ok.
        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("name", "Field cannot be empty");
        }

        if (exception.getErrors().size() > 0) {
            throw exception;
        }
        obj.setName(txtName.getText()); // Gets the name from the form.

        return obj;
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity is null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("name")) {
            errorLabelName.setText(errors.get("name"));
        }
    }
}
