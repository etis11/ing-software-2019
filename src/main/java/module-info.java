open module project{
    requires javafx.fxml;
    requires javafx.controls;
    requires java.naming;
    requires java.management;
    requires gson;
    requires java.logging;

    exports gui to javafx.graphics, javafx.fxml;
}