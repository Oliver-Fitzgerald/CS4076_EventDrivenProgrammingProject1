package com.mycompany.client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectScreen extends Application {

    private ClientServerConnection con ;
    @FXML
    public ReactiveButton connectButton ;

    @FXML
    private void initialize(){
        connectButton.setText("Connect") ;
    }
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(ConnectScreen.class.getResource("ConnectScreen.fxml")) ;
        Scene scene = new Scene(fxmlLoader.load(),500,400) ;

        con = new ClientServerConnection() ;



        stage.setTitle("Class Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){launch();}
}
