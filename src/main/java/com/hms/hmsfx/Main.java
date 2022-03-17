package com.hms.hmsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Connection.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1480,800);
        String css = this.getClass().getResource("styles.css").toExternalForm();
        System.out.println(this.getClass().getResource("styles.css").toExternalForm());
        scene.getStylesheets().add(css);
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}