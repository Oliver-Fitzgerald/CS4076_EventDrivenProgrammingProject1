package com.mycompany.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket sock;
    private boolean running;

    public ClientHandler(Socket sock){
        this.sock = sock;
        this.running = true;
    }
    @Override
    public void run(){
        try(BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true)) {
            while(running) {
                try {
                    //I assume that the message passed will be a string.
                    //I assume that it will start with three chars
                    //indicating the operation to perform with the data after it
                    //these codes being 'add' 'rem' or 'dis'
                    //the format of the data and how it is handled after is up
                    //to you.
                    String message = in.readLine();
                    String code = message.substring(0, 3);
                    String data = message.substring(4);
                    switch (code) {
                        case "add": //add module
                            out.println(Server.addModule(data));
                            System.out.println("10");
                            break;
                        case "rem": //remove module
                            break;
                        case "dis": //display class
                            out.println(Server.displayCourse(data));
                            System.out.println("00");
                            break;
                        case "ter": //terminate connection
                            try {
                                out.println("30");
                                System.out.println("Terminate connection received");
                                sock.close();
                            } catch (IOException e) {
                                System.out.println("Unable to close connection.");
                                System.exit(1);
                            }
                            running = false;
                            break;
                        default:
                            throw new IncorrectActionException("-1");
                    }
                }
                catch(IncorrectActionException e){
                    System.out.println(e.getMessage());
                    out.println(e.getMessage());
                }
                catch(IOException e){
                    System.out.println(e.getMessage());
                    sock.close();
                    running = false;
                }
                catch(NullPointerException e){
                    System.out.println("Client forcibly closed connection");
                    sock.close();
                    running = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error getting IO from client.");
            running = false;
        }
        System.out.println("Closing connection...");
    }
}
