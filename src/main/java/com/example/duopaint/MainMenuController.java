package com.example.duopaint;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.*;

import static com.example.duopaint.StaticData.*;

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
        StaticData.mainMenuController = this;
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

    @FXML
    public void clickHelp(ActionEvent event) throws IOException {
        mainStackPane.setVisible(false);
        overlayHelp.setVisible(true);
    }
    @FXML
    public void exitHA(ActionEvent event) throws IOException {
        mainStackPane.setVisible(true);
        overlayHelp.setVisible(false);
        //overlayAbout.setVisible(false);
    }

    public void joinRoom() {
        StaticData.hostListener = new HostListener();
        StaticData.hostListener.start();
        try {
            Thread.sleep(1000);
            String hostIP = StaticData.hostListener.getHostIP();
            if (hostIP == null) {
                new Alert(Alert.AlertType.ERROR, "Coudn't find host").showAndWait();
                hostListener.disableSearchMode();
                hostListener.interrupt();
                return;
            }
            StaticData.playerName = nameTextField.getText();
            if (StaticData.playerName.isEmpty() || StaticData.playerName == null) {
                StaticData.playerName = "player";
            }
            System.out.printf("asa");
            var withserver = new SocketWrapper(hostIP, 55000);
            try {
                StaticData.clientRead = new ClientRead(withserver);
                StaticData.clientWrite = new ClientWrite(withserver);
            } catch (UnknownHostException | ConnectException e) {
                System.out.println("Host " + hostIP + " is unreachable");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WaitingRoom.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            StaticData.stage.setScene(scene);
            StaticData.scene = scene;
            StaticData.stage.show();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    public void createRoomButtonPressed(ActionEvent event) {
        if (ServerData.broadcaster == null) {
            StaticData.isHost = 1;
            ServerData.broadcaster = new HostBroadcaster();
            ServerData.broadcaster.start();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("Room created. Broadcasting...");
            new GameServer();
        } else {
            System.out.println("Room already exists.");
            return;
        }
        joinRoom();
    }

    @FXML
    public void joinRoomButtonPressed(ActionEvent event) {
        StaticData.isHost = 0;
        joinRoom();
    }

    @FXML
    public void ExitButtonPressed (ActionEvent event) {
        try {
            mainApp.quitgame((Stage) menuExitButton.getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Skribbl.io");
        alert.setHeaderText("Skribbl.io- Local multiplayer drawing and guessing game");
        alert.setContentText("Version 0.5f (alpha)\n" +
                "Developed by Raad Sharar and Md Nayem Sarker\n" +
                "© 1-2 Term Project");

        alert.showAndWait();
    }
}
