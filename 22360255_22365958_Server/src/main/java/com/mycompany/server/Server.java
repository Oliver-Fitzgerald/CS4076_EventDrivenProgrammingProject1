package com.mycompany.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private static ServerSocket servSock;
    private static final int PORT = 30572;
    private static int numConnections = 0;
    private static boolean loading;

    public static void main(String[] args) {
        Thread startServerLoadPrint = new Thread(new loadingText("Starting Server", "Server Started Successfully"));
        startServerLoadPrint.start();

        try {
            servSock = new ServerSocket(PORT);
        }catch(IOException e) {
            System.out.println("Can't connect to port.");
            System.exit(1);
        }
        finally {
            loading = false;
            try {
                startServerLoadPrint.join();
            }
            catch(InterruptedException e){
                System.out.println("Failed to join loading text");
            }
        }
        run();
    }

    private static void run(){
        loading = true;
        while(loading){
            try{
                Socket link = servSock.accept();
                numConnections++;
                System.out.println("Client " + numConnections + " connected.");

                Thread clientThread = new Thread(new ClientHandler(link));
                clientThread.start();
            }catch(IOException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private static class loadingText implements Runnable {
        String startMessage;
        String endMessage;

        public loadingText(String startMessage){
            this.startMessage = startMessage;
            this.endMessage = "";
        }

        public loadingText(String startMessage, String endMessage){
            this(startMessage);
            this.endMessage = endMessage;
        }

        @Override
        public void run(){
            try{
                int i = 1;
                System.out.print(this.startMessage);
                while(loading) {
                    System.out.print(".".repeat(i));
                    System.out.flush();
                    Thread.sleep(500);
                    System.out.print("\b".repeat(i));
                    i = (i % 3) + 1;
                }
            }
            catch(InterruptedException e){
                loading = false;
            }
            System.out.println("\n" + endMessage);
        }
    }

    private static class ClientHandler implements Runnable {
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
                                System.out.println(data);
                                out.println("10");
                                break;
                            case "rem": //remove module
                                break;
                            case "dis": //display class
                                break;
                            case "ter": //terminate connection
                                try {
                                    System.out.println("Closing connection");
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
                        out.println(e.getMessage());
                    }
                    catch(IOException e){
                        System.out.println(e.getMessage());
                        sock.close();
                        running = false;
                    }
                    catch(NullPointerException e){
                        System.out.println(e.getMessage());
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
}