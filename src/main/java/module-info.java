module me.reszkojr.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.reszkojr.workshopjavafxjdbc to javafx.fxml;
    exports me.reszkojr.workshopjavafxjdbc;

    opens me.reszkojr.workshopjavafxjdbc.controllers to javafx.fxml;
    exports me.reszkojr.workshopjavafxjdbc.controllers;
}