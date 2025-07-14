package com.example.duopaint;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Application.class.getResource("Scene1.fxml"));
        stage.setTitle("Hello World");
        Scene scene = new Scene(root);
        //String css = getClass().getResource("hello-view.css").toExternalForm();
        //scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest((e) -> {
            e.consume();
            try {
                logout(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void logout(Stage stage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logout");
        alert.setContentText("Are you sure you want to logout?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("You closed the window");
            stage.close();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}