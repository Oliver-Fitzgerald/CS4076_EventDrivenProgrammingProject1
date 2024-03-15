package com.mycompany.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectScreen {

    private Scene nextScene ;
    private Stage primaryStage ;
    @FXML
    public ReactiveButton connectButton = new ReactiveButton();

    @FXML
    private void initialize(){
        connectButton.setText("Connect");
        connectButton.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            ClientServerConnection connection = new ClientServerConnection();
            Client.UIInitialization(primaryStage,nextScene);
        });
    }

    public ConnectScreen(Stage primaryStage, Scene nextScene){
        this.primaryStage = primaryStage ;
        this.nextScene = nextScene ;
    }
    public Scene connectScreen() {

        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(ConnectScreen.class.getResource("ConnectScreen.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 500, 400);
            scene.getStylesheets().add(String.valueOf(Client.class.getResource("connectScreenStyle.css"))) ;

        }catch (IOException e){
            System.out.println("Failed to load connect screen");
        }



        return scene;
    }
}
