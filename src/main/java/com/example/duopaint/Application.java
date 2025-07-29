package com.example.duopaint;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        stage.setTitle("skribbl!");
        Scene scene = new Scene(root);
        StaticData.stage = stage;
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("favicon.png")));
        MainMenuController menu = loader.getController();
        menu.mainApp = this;
        stage.setOnCloseRequest((e) -> {
            e.consume();
            try {
                quitgame(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void quitgame(Stage stage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Quit Game?");
        alert.setContentText("Are you sure you want to exit?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            stage.close();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}