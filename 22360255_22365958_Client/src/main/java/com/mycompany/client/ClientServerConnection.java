package com.mycompany.client;

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
    private static Client client ;
    static boolean terminate = false ;

    /**
     * Opens connection with server
     **/
    public void connect(Client client){

        this.client = client ;

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

        try
        {
            link = new Socket(host,PORT); //Opens socket and connects with server

            while (terminate == false) {


                //Send Message
                if (client.getAddSend() == true)
                    handle(client.getAddMessage(),link);
                if (client.getRemSend() == true)
                    handle(client.getRemMessage(),link);



            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }finally {

            try
            {
                System.out.println("\n* Closing connection... *");
                link.close(); //Closes connection with server

            }catch(IOException e)
            {
                System.out.println("Unable to disconnect/close!");
                System.exit(-1);
            }

        }



    }


    public static void handle(String sendMessage,Socket link){
        try {
            PrintWriter out = new PrintWriter(link.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            String response;
            String code = sendMessage.substring(0, 3);;

            //send message
            out.println(sendMessage);
            client.reSetSend();

            //receive message
            response = in.readLine(); //response == 1 success;else exception message
            //ter
            if (code.equals("ter") && response.equals("1"))
                terminate = true;
            //add
            //rem
            //dis
            
        }
        catch (IOException e){
            System.out.println("Whoopsy in handle");
        }
    }

}
