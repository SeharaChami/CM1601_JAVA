module com.example.tuktukapp {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    opens tuktukjava to javafx.graphics, javafx.fxml;
    opens tuktukjava.controllers to javafx.fxml;

    exports tuktukjava;
}