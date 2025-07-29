package com.example.duopaint;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.duopaint.StaticData.*;
import static com.example.duopaint.StaticData.strokeWidth;

public class GameSceneController {
    @FXML
    public BorderPane gamingBG;

    @FXML
    public Canvas drawingCanvas;
    @FXML
    private ListView<Player> playerListView;

    @FXML
    private ImageView clearButton;

    @FXML
    private ImageView eraseButton;

    public static final ObservableList<Player> players =
            FXCollections.observableArrayList();

    @FXML
    public ColorPicker colorPicker;

    @FXML
    public Slider widthSlider;

    @FXML
    public TextArea textArea;

    @FXML
    public TextField textField;

    @FXML
    public Label timeLabel;

    @FXML
    public Label guessLabel;

    @FXML
    public Label choice1, choice2, choice3;

    @FXML
    public Label whoIsChoosing;

    @FXML
    public BorderPane otherChoosingBG;

    @FXML
    public BorderPane meChoosingBG;

    @FXML
    public void initialize() {
        playerListView.setItems(players);
        updatePlayersFromServer(StaticData.players);
        graphicsContext = drawingCanvas.getGraphicsContext2D();
        setupDrawing();
        SguessLabel = guessLabel;
        StimeLabel = timeLabel;
        StextArea = textArea;

        widthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            strokeWidth = newVal.doubleValue();
            strokeColor = colorPicker.getValue();
            graphicsContext.setLineWidth(strokeWidth);  // update stroke width
        });

        graphicsContext.setLineWidth(3);
        strokeWidth = 3;

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

        clearButton.setOnMouseClicked(event -> {
            System.out.println("clear clicked!");
            // your action here
            clearImage(null);
        });

        eraseButton.setOnMouseClicked(event -> {
            System.out.println("erase clicked!");
            eraseButtonPressed(null);
        });

        choice1.setOnMouseClicked(event -> {
            toWrite.add(new Message(Message.Type.WORD_CHOSEN, playerName, choice1.getText()));
        });

        choice2.setOnMouseClicked(event -> {
            toWrite.add(new Message(Message.Type.WORD_CHOSEN, playerName, choice2.getText()));
        });

        choice3.setOnMouseClicked(event -> {
            toWrite.add(new Message(Message.Type.WORD_CHOSEN, playerName, choice3.getText()));
        });
    }

    public static void updatePlayersFromServer(List<Player> listFromServer) {
        Platform.runLater(() -> {
            players.setAll(listFromServer);
            gameSceneController.playerListView.refresh();
        });

    }


    private void setupDrawing() {
        AtomicReference<Double> lastX = new AtomicReference<>((double) 0);
        AtomicReference<Double> lastY = new AtomicReference<>((double) 0);
        drawingCanvas.setOnMousePressed(e -> {
            lastX.set(e.getX());
            lastY.set(e.getY());
        });

        drawingCanvas.setOnMouseDragged(e -> {
            double x = e.getX();
            double y = e.getY();
            if (isDrawing == 1) {
                sendDrawCommand(lastX.get(), lastY.get(), x, y, strokeColor, strokeWidth);
            }
            lastX.set(x);
            lastY.set(y);
        });
    }

    @FXML
    public void clearImage(ActionEvent event) {
        if (isDrawing == 1) {
            System.out.println("clear cmd given");
            toWrite.add(new Message(Message.Type.CLEAR_CMD, playerName, null));
        }
    }

    private void sendDrawCommand(double x1, double y1, double x2, double y2, Paint sc, double sw) {
        //System.err.println(x1 + " " + y1 + " " + x2 + " " + y2);
        System.err.println(toWrite.size());
        toWrite.add(new Message(Message.Type.DRAW_CMD, playerName, new Line(x1, y1, x2, y2, sc, sw)));
    }


    @FXML
    private void handleColorChange() {
        strokeColor = colorPicker.getValue();
        graphicsContext.setStroke(strokeColor);
        strokeWidth = widthSlider.getValue();
    }

    @FXML
    public void eraseButtonPressed(ActionEvent event) {
        Color eraserColor = Color.WHITE;
        double eraserWidth = widthSlider.getMax();
        strokeColor = eraserColor;
        strokeWidth = eraserWidth;
        graphicsContext.setStroke(strokeColor);
        graphicsContext.setLineWidth(strokeWidth);
    }

    @FXML
    public void guessSubmitted(ActionEvent event) {
        if (isDrawing != 1) {
            toWrite.add(new Message(Message.Type.CHAT, playerName, textField.getText()));
        }
        textField.clear();
    }

    public void showResult() {
        players.sort((p1, p2) -> Integer.compare(p2.score, p1.score));
        playerListView.refresh();
    }


}
