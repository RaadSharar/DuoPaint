package com.example.duopaint;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.duopaint.GameSceneController.players;

public class ResultSceneController {

    @FXML
    private ListView<Player> resultListView;

    @FXML
    public Label backToMenu;

    @FXML
    public void initialize() {
        System.out.println("Players in StaticData:");
        for (Player p : StaticData.players) {
            System.out.println(p.getName() + " " + p.score);
        }
        Platform.runLater(() -> {
            backToMenu.setOnMouseClicked(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.setScene(scene);
                    stage.show();

                    StaticData.fullReset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ObservableList<Player> sortedPlayers = FXCollections.observableArrayList(StaticData.players);
            sortedPlayers.sort((p1, p2) -> Integer.compare(p2.score, p1.score));
            resultListView.setItems(sortedPlayers);
            resultListView.setCellFactory(lv -> new CustomPlayerCell(sortedPlayers));
        });

    }
}