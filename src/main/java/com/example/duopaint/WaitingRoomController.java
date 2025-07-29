package com.example.duopaint;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class WaitingRoomController {
    @FXML
    private ListView<Player> playerListView;

    public static final ObservableList<Player> players =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        playerListView.setItems(players);

        playerListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label nameLabel = new Label(player.getName());
                    nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                    Label scoreLabel = new Label(String.valueOf(player.score));
                    scoreLabel.setStyle("-fx-font-size: 16px;");

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS); // this pushes scoreLabel to the right

                    HBox hBox = new HBox(10, nameLabel, spacer, scoreLabel);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.setMinHeight(40); // control height of each cell
                    setGraphic(hBox);
                }
            }
        });
    }

    public static void updatePlayersFromServer(List<Player> listFromServer) {
        Platform.runLater(() -> players.setAll(listFromServer));
    }

    public void requestStartGame() {
        try {
            StaticData.toWrite.put(new Message(Message.Type.START, null, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void startGameClicked() {
        System.out.println("startGameClicked");
        if (StaticData.isHost == 1) {
            requestStartGame();
        }
        // ...
    }

    public void endGame() {
        // ...
    }
}
