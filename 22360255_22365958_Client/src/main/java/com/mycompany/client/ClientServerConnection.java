package com.mycompany.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import static javafx.application.Application.launch;

public class ClientServerConnection {
    private InetAddress host;
    private static final int PORT = 30572; //0 -> 1023 are reserved.
    private Socket sock;
    private PrintWriter out;
    private BufferedReader in;
    /**
     * Opens connection with server
     **/
    public ClientServerConnection(){
        try
        {
            host = InetAddress.getLocalHost(); //gets the local address of the device
        }
        catch(UnknownHostException e)
        {
            System.out.println("Host ID not found!");
            System.exit(-1);
        }

        try{
            sock = new Socket(host, PORT);
            out = new PrintWriter(sock.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        }
        catch(IOException e){
            System.out.println("Failed to connect to server. Exiting...");
            System.exit(1);
        }
    }

    public String send(String toSend){
        String response = "";
        try{
            out.println(toSend);
            //receive message
            try {
                response = in.readLine();
            } catch(SocketException e){
                System.out.println("Server closed connection\nExiting...");
                System.exit(1);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

    public void terminate(){
        this.out.close();
        try {
            this.in.close();
        }
        catch(IOException e){
            System.out.println("Error closing BufferedReader.");
        }
        try {
            this.sock.close();
        }
        catch(IOException e){
            System.out.println("Error closing connection.");
        }
    }
}
