package com.example.duopaint;


import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

class CustomPlayerCell extends ListCell<Player> {
    private final ObservableList<Player> sortedPlayers;

    public CustomPlayerCell(ObservableList<Player> sortedPlayers) {
        this.sortedPlayers = sortedPlayers;
    }

    @Override
    protected void updateItem(Player player, boolean empty) {
        super.updateItem(player, empty);
        if (empty || player == null) {
            setGraphic(null);
            return;
        }

        int index = getIndex();
        int rank = 1;
        for (int i = 0; i < index; i++) {
            if (sortedPlayers.get(i).score > player.score) {
                rank++;
            }
        }

        Label rankLabel = new Label("#" + rank);
        rankLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: gray; -fx-min-width: 40px;");

        Label name = new Label(player.getName());
        name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label score = new Label(String.valueOf(player.score));
        score.setStyle("-fx-font-size: 16px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox hBox = new HBox(10, rankLabel, name, spacer, score);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setMinHeight(40);
        hBox.setStyle("-fx-padding: 5; -fx-background-radius: 8;");

        switch (rank) {
            case 1 -> hBox.setStyle(hBox.getStyle() + "-fx-background-color: #FFD700;"); // Gold
            case 2 -> hBox.setStyle(hBox.getStyle() + "-fx-background-color: #C0C0C0;"); // Silver
            case 3 -> hBox.setStyle(hBox.getStyle() + "-fx-background-color: #CD7F32;"); // Bronze
        }

        setGraphic(hBox);
    }
}
