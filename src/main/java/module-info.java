module com.example.duopaint {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.duopaint to javafx.fxml;
    exports com.example.duopaint;
}