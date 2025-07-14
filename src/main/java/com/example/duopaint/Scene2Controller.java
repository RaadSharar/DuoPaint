package com.example.duopaint;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Scene2Controller {
    @FXML
    Label shownName;
    public void changeText(String u) {
        shownName.setText("Hello: " + u);
    }
}
