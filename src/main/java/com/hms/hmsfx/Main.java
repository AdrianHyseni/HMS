package com.hms.hmsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;


import java.io.IOException;

public class Main extends Application {

    private static Stage stg;

    public String getCss(){
        String css = this.getClass().getResource("styles.css").toExternalForm();
        return css;
    }

    @Override
    public void start(Stage stage) throws IOException {
        stg  = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1480,800);
        scene.getStylesheets().add(getCss());
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        pane.getStylesheets().add(getCss());
        stg.getScene().setRoot(pane);

    }


    public static void main(String[] args) {
        launch();
    }
}