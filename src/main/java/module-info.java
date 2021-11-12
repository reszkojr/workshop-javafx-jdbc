module me.reszkojr.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.reszkojr.workshopjavafxjdbc to javafx.fxml;
    exports me.reszkojr.workshopjavafxjdbc;
}