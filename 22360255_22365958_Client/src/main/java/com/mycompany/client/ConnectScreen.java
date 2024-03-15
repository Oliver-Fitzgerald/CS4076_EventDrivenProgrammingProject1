package com.mycompany.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectScreen {
    @FXML
    public ReactiveButton connectButton;

    public ConnectScreen(){
        connectButton = new ReactiveButton();
    }
    public Scene connectScreen() {
        Scene scene = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConnectScreen.fxml"));
        try {
            scene = new Scene(loader.load(), 500, 400);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("connectScreenStyle.css"))) ;

        }catch (IOException e){
            e.printStackTrace();
        }

        return scene;
    }
}
