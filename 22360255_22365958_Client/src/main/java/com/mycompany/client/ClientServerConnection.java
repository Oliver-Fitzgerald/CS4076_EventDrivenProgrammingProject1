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

public class ClientServerConnection {
    static InetAddress host;
    static final int PORT = 1024; //0 -> 1023 are reserved

    /**
     * Opens connection with server
     **/
    public void connect(){
        try
        {
            host = InetAddress.getLocalHost(); //gets the local address of the device
        }
        catch(UnknownHostException e)
        {
            System.out.println("Host ID not found!");
            System.exit(-1);
        }

        run();
    }

    public static void run(){
        Socket link = null;
        boolean terminate = false ;

        try
        {
            link = new Socket(host,PORT); //Opens socket and connects with server

            while (terminate == false) {

                //Send Message

                //Receive Response
                //if (response == ter)
                //  closeConnection;
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }



    }

    public static void closeConnection(){
       /*

        try
        {
            System.out.println("\n* Closing connection... *");
            link.close(); //Closes connection with server

        }catch(IOException e)
        {
            System.out.println("Unable to disconnect/close!");
            System.exit(-1);
        }

        */
    }



}
