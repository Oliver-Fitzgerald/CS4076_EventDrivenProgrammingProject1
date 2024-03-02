module com.mycompany.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.mycompany.client to javafx.fxml;
    exports com.mycompany.client;
}