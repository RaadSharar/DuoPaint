package com.example.duopaint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, 420,420, Color.BLUE);
        Image wow = new Image(getClass().getResourceAsStream("/images/wow.png"));
        stage.getIcons().add(wow);
        stage.setTitle("DuoPainting");
        Text text = new Text();
        text.setText("\"wow i am so cool nigga\"");
        text.setX(50);
        text.setY(50);
        text.setFont(Font.font("Verdana", 50));

        text.setFill(Color.LIMEGREEN);
        Line line = new Line();
        Line line2 = new Line();
        line.setStartX(100);
        line.setStartY(100);
        line.setEndY(100);
        line.setEndX(500);
        line2.setStrokeWidth(5);
        line2.setStartX(250);
        line2.setStartY(100);
        line2.setEndY(100);
        line2.setEndX(350);
        line.setStrokeWidth(5);
        //line.setStroke(Color.RED);
        line.setRotate(45);

        Image hotimage = new Image(getClass().getResourceAsStream("/images/hotdogimg.jpeg"));
        ImageView dogview = new ImageView(hotimage);
        dogview.setX(100);
        dogview.setY(500);
        root.getChildren().add(dogview);
        root.getChildren().add(line2);
        root.getChildren().add(line);
        root.getChildren().add(text);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}