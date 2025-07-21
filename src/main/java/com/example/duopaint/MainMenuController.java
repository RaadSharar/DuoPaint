package com.example.duopaint;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class MainMenuController {

    @FXML
    private Button changeLeft;

    @FXML
    private Button changeRight;

    @FXML
    private Button createGameButton;

    @FXML
    private StackPane mainStackPane;

    @FXML
    private Button menuAboutButton;

    @FXML
    private BorderPane menuBG;

    @FXML
    private Button menuExitButton;

    @FXML
    private Button menuHelpButton;

    @FXML
    private VBox menuHelpVBox;

    @FXML
    private VBox midVBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private BorderPane overlayHelp;

    @FXML
    private Button playButton;

    @FXML
    private ImageView selectImageView;

    @FXML
    private HBox selectionHBox;

    @FXML
    private Button exitHelp;

    @FXML
    private ImageView helpImageView;
    @FXML
    private ToggleGroup helpStepGroup;

    @FXML
    public void initialize() {
        helpStepGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                int index = helpStepGroup.getToggles().indexOf(newToggle) + 1;
                System.out.println(index);
                String imagePath = "helpSteps/step" + index + ".gif";
                URL imageUrl = getClass().getResource(imagePath);

                if (imageUrl != null) {
                    helpImageView.setImage(new Image(imageUrl.toExternalForm()));
                } else {
                    System.err.println("Missing image at: " + imagePath);
                }
            }
        });
    }

    public void clickHelp(ActionEvent event) throws IOException {
        mainStackPane.setVisible(false);
        overlayHelp.setVisible(true);
    }
    public void exitHA(ActionEvent event) throws IOException {
        mainStackPane.setVisible(true);
        overlayHelp.setVisible(false);
        //overlayAbout.setVisible(false);
    }
}
