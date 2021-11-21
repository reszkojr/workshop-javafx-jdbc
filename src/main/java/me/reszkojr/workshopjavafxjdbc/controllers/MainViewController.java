package me.reszkojr.workshopjavafxjdbc.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import me.reszkojr.workshopjavafxjdbc.Main;
import me.reszkojr.workshopjavafxjdbc.model.services.DepartmentService;
import utils.Alerts;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction() {
        //loadView("absoluteName");
        System.out.println("onMenuItemSellerAction");
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        loadView("gui/DepartmentList.fxml", (DepartmentListController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();
        });

    }

    @FXML
    public void onMenuItemAboutAction() {
        loadView("gui/About.fxml", x -> {});
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(absoluteName));
            VBox newVBox = loader.load();

            Scene mainScene = Main.getMainScene();

            // Gets the content of the mainScene variable, so we can substitute it with another content that we want
            VBox mainVBox = ((VBox) ((ScrollPane) mainScene.getRoot()).getContent());

            // Gets the first children of the mainVBox
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IOException", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}