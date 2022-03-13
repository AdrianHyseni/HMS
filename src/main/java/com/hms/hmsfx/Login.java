package com.hms.hmsfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Login {

    @FXML
    private Label label;

    @FXML
    protected void onHelloButtonClick() {
        label.setText("Welcome to JavaFX Application!");
    }
}
