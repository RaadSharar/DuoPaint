package com.example.duopaint;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
}
