module org.example.project_ppoo_java {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.project_ppoo_java to javafx.fxml;
    exports org.example.project_ppoo_java;
}