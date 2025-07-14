package com.example.duopaint;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scene1Controller {
    @FXML
    Button changeButton;
    @FXML
    ImageView scene1image;
    Image image2 = new Image(getClass().getResourceAsStream("/images/hotdogimg.jpeg"));
    Image image1 = new Image(getClass().getResourceAsStream("/images/wow.png"));
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int changecount = 0;
    public void changePic(ActionEvent e) throws IOException {
        if (changecount % 2 == 0) {
            scene1image.setImage(image2);
        } else  {
            scene1image.setImage(image1);
        }
        changecount++;
    }

}