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
                        for (Player p : StaticData.players) {
                            System.out.println(p.name + " " + p.score);
                        }
                        if (nowscene == 3) {
                            System.out.println(nowscene + " received player list");
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
                        //System.out.println("draw cmd received");
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
                        //System.out.println("clear cmd received");
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
                    case Message.Type.CHAT -> {
                        Platform.runLater(() -> {
                            if (message.sender == null) {
                                StextArea.appendText((String) message.payload + "\n");
                            } else {
                                StextArea.appendText(message.sender + ": " + message.payload + "\n");
                            }
                        });
                    }
                    case Message.Type.TIME_REM ->  {
                        Platform.runLater(() -> {
                            StimeLabel.setText((String) message.payload);
                        });
                    }
                    case Message.Type.ROUND_STARTS -> {
                        if (message.payload instanceof Round) {
                            Round round = (Round) message.payload;
                            if (round.artist.equals(playerName)) {
                                isDrawing = 1;
                                Platform.runLater(() -> {
                                    SguessLabel.setText(round.word);
                                });
                            } else {
                                String wow = "";
                                for (int i = 0; i < round.word.length(); i++) {
                                    wow = wow + "-";
                                }
                                String finalWow = wow;
                                Platform.runLater(() -> {
                                    StaticData.SguessLabel.setText(finalWow);
                                });
                            }
                        }
                    }
                    case ROUND_ENDS -> {
                        isDrawing = 0;
                    }
                    case RESULT -> {
                        gameSceneController.showResult();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ClientRead Exception: " + e);;
        }
    }
}
