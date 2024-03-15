package com.mycompany.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectScreen {

    @FXML
    public ReactiveButton connectButton ;

    @FXML
    private void initialize(){
        connectButton.setText("Connect") ;

    }
    public Scene ConnectScreen() throws IOException{


        FXMLLoader fxmlLoader = new FXMLLoader(ConnectScreen.class.getResource("ConnectScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        scene.getStylesheets().add(String.valueOf(Client.class.getResource("connectScreenStyle.css"))) ;

        connectButton.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            //do something

        });

        return scene;
    }
}
