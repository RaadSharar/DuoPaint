package com.example.duopaint;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StaticData {
    public static int nowscene = 1;
    public static int isHost = 0;
    public static int isDrawing = 0;
    public static String playerName = "player";
    public static GameSceneController gameSceneController;
    public static WaitingRoomController waitingRoomController;
    public static MainMenuController mainMenuController;
    public static HostListener hostListener = null;
    public static Scene scene;
    public static Stage stage;
    public static List<Player> players;
    public static ClientRead clientRead;
    public static BlockingQueue<Message> toWrite = new LinkedBlockingQueue<>();
    public static ClientWrite clientWrite;
    public static SocketWrapper withserver;
    public static GraphicsContext graphicsContext;
    public static Paint strokeColor = Color.BLACK;
    public static double strokeWidth;
    public static Label SguessLabel;
    public static String guessWord;
    public static TextArea StextArea;
    public static Label StimeLabel;
    public static int hint1;
    public static int hint2;
    public static Application mainApp;
    public static int editDP(String text, String guessWord) {
        if (text.length() == guessWord.length()) {
            int s = text.length();
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == guessWord.charAt(i)) {
                    s--;
                }
            }
            return s;
        } else {
            return -1;
        }
    }

    public static void fullReset() throws IOException {
        nowscene = 1;
        isHost = 0;
        isDrawing = 0;
        playerName = "player";

        gameSceneController = null;
        waitingRoomController = null;
        mainMenuController = null;
        hostListener = null;

        scene = null;

        if (players != null) {
            players.clear();
        }
        players = null;
        if (clientRead != null) {
            clientRead.interrupt();
            clientRead = null;
        }
        if (clientWrite != null) {
            clientWrite.interrupt();
            clientWrite = null;
        }
        toWrite.clear();
        withserver = null;
        graphicsContext = null;
        strokeColor = Color.BLACK;
        strokeWidth = 0;
        SguessLabel = null;
        guessWord = null;
        StextArea = null;
        StimeLabel = null;
        hint1 = 0;
        hint2 = 0;
    }
}
