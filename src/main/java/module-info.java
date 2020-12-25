module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
	requires javax.mail;
	requires java.desktop;

    opens application to javafx.fxml;
    exports application;
    opens User to javafx.fxml;
    exports User;
    opens Admin to javafx.fxml;
    exports Admin;
}
