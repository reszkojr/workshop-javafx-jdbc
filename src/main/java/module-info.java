module me.reszkojr.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens me.reszkojr.workshopjavafxjdbc to javafx.fxml;
    exports me.reszkojr.workshopjavafxjdbc;

    opens me.reszkojr.workshopjavafxjdbc.controllers to javafx.fxml;
    exports me.reszkojr.workshopjavafxjdbc.controllers;

    opens me.reszkojr.workshopjavafxjdbc.model.entities to javafx.base;
    exports me.reszkojr.workshopjavafxjdbc.model.entities;
}