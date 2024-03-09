package com.mycompany.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static javafx.application.Application.launch;

public class ClientServerConnection extends Application {
    static InetAddress host;
    static final int PORT = 1234;
    Label label = new Label("Response From Server Will Display Here");
    TextField textField = new TextField("");
    Button button = new Button("Send");

    @Override
    public void start(Stage stage) {





        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try
                {
                    host = InetAddress.getLocalHost();
                }
                catch(UnknownHostException e)
                {
                    System.out.println("Host ID not found!");
                    System.exit(1);
                }
                Socket link = null;
                try
                {
                    link = new Socket(host,PORT);
                    //link = new Socket( "192.168.0.59", PORT);
                    BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                    PrintWriter out = new PrintWriter(link.getOutputStream(),true);

                    String message = null;
                    String response= null;

                    System.out.println("Enter message to be sent to server: ");
                    message =  textField.getText().toString();
                    out.println(message);
                    response = in.readLine();
                    label.setText(response);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        System.out.println("\n* Closing connection... *");
                        link.close();				//Step 4.
                    }catch(IOException e)
                    {
                        System.out.println("Unable to disconnect/close!");
                        System.exit(1);
                    }
                }
            }});





        VBox box= new VBox( textField, button, label);
        var scene = new Scene(box, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch() ;
    }

}
