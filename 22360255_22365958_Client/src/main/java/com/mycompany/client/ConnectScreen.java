package com.mycompany.client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectScreen extends Application {

    @FXML
    ReactiveButton connectButton = new ReactiveButton("Connect") ;
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(ConnectScreen.class.getResource("ConnectScreen.fxml")) ;
        Scene scene = new Scene(fxmlLoader.load(),500,400) ;

        stage.setTitle("Class Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){launch();}
}
