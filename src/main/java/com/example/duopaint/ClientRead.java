package com.example.duopaint;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import static com.example.duopaint.StaticData.*;

public class ClientRead extends Thread {
    public SocketWrapper socketWrapper;
    public ClientRead(SocketWrapper socketWrapper) throws IOException {
        this.socketWrapper = socketWrapper;
        this.start();
    }
    public void run() {
        try {
            while (true) {
                Message message = (Message) socketWrapper.read();
                switch (message.type) {
                    case Message.Type.NAME_PROMT -> toWrite.put(new Message(Message.Type.JOIN, playerName, null));
                    case Message.Type.PLAYER_LIST -> {
                        StaticData.players = (List<Player>) message.payload;
                        if (nowscene == 3) {
                            GameSceneController.updatePlayersFromServer(StaticData.players);
                        } else {
                            WaitingRoomController.updatePlayersFromServer(StaticData.players);
                        }
                    }
                    case Message.Type.START -> {
                        Platform.runLater(() -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
                                Scene gameScene = new Scene(loader.load());
                                GameSceneController controller = loader.getController();
                                StaticData.gameSceneController = controller;
                                StaticData.stage.setScene(gameScene);
                                StaticData.stage.show();
                                nowscene = 3;
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.err.println(e.getMessage());
                                System.err.println("start failed");
                            }
                        });
                    }
                    case Message.Type.DRAW_CMD -> {
                        System.out.println("draw cmd received");
                        Platform.runLater(() -> {
                            try {
                               Line line = (Line) message.payload;
                               graphicsContext.setStroke(Color.web(line.strokeColor));
                               graphicsContext.setLineWidth(line.strokeWidth);
                               graphicsContext.strokeLine(line.x1, line.y1, line.x2, line.y2);
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.err.println(e.getMessage());
                                System.err.println("draw failed");
                            }
                        });
                    }
                    case Message.Type.CLEAR_CMD -> {
                        System.out.println("clear cmd received");
                        Platform.runLater(() -> {
                            try {
                                graphicsContext.clearRect(0, 0, 490, 452);
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.err.println(e.getMessage());
                                System.err.println("clear failed");
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ClientRead Exception: " + e);;
        }
    }
}
