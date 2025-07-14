module com.example.duopaint {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;


    opens com.example.duopaint to javafx.fxml;
    exports com.example.duopaint;
}