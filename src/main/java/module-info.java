open module project {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.naming;
    requires java.management;
    requires gson;
    requires java.logging;
    requires java.rmi;
    requires java.sql;

    exports view.gui to javafx.graphics, javafx.fxml;
}
