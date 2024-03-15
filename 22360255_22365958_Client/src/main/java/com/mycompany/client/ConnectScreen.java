package com.mycompany.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectScreen extends BorderPane {
    @FXML
    public ReactiveButton connectButton;

    @FXML
    private void initialize(){
        connectButton.setText("Connect");
    }
    public ConnectScreen(){
        connectButton = new ReactiveButton();

    }

    public Scene connectScreen() {

        Scene scene = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConnectScreen.fxml"));
        try {
            loader.setRoot(this);
            loader.setController(ConnectScreen.this);

            loader.load();

            this.getStylesheets().add(String.valueOf(getClass().getResource("connectScreenStyle.css"))) ;

        }catch (IOException e){
            System.out.println("Failed to load connect screen");
            e.printStackTrace();
        }
    }
    public Scene generateScene() {
        return new Scene(this, 500, 400);
    }
}
