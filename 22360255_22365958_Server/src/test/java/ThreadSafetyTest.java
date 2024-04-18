import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class ThreadSafetyTest {
    private Thread serverThread;
    private static final int PORT = 30572;

    @BeforeEach
    void setUp() {
        //Clear the xml file of contents created by any test
        String filePath = System.getProperty("user.home") + File.separator + "Documents" +
                File.separator + "Class Scheduler" + File.separator + "courses.xml";

        File file = new File(filePath);

        try {
            FileWriter writer = new FileWriter(file, false);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while emptying the file.");
            e.printStackTrace();
        }

        // Start the server in a separate thread for proper simulation
        serverThread = new Thread(() -> Server.main(new String[0]));
        serverThread.start();
    }

    @AfterEach
    void tearDown() {
        //Close the server gracefully
        Server.closeServer();
        //Close the thread gracefully
        serverThread.interrupt();
        try {
            serverThread.join();
            System.out.println("termination reached");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("test ended");
    }

    //Errors Found:
    //Inadvertantly found that if the courses.xml file doesn't exist, but the folder does, then rips.
    //Remove and add are not synchronized even though they are on the same lock
    @DisplayName("Add 1, Remove 1")
    @Test
    @Timeout(value=10, unit=TimeUnit.SECONDS)
    void AddRemoveSuccessTest(){
        //String to send
        String addData = "add:2024-04-18,LM051,CS4076,CSG001,13:00";
        String removeData = "rem:2024-04-18,LM051,CS4076,CSG001,13:00";

        //responses to check
        String[] r1 = {""};
        String[] r2 = {""};

        //Creating clients
        Thread client1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientServerConnection con = new ClientServerConnection();

                r1[0] = con.send(addData);

                if(con.send("ter:---").equals("30"))
                    con.terminate();
            }
        });

        Thread client2 = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientServerConnection con = new ClientServerConnection();

                r2[0] = con.send(removeData);

                if(con.send("ter:---").equals("30"))
                    con.terminate();
            }
        });

        client1.start();
        client2.start();

        try{
            client2.join();
            client1.join();

            //Having client 1 end first did not fix error 2

            //The output for the above is:
            /*
                Starting Server
                Server Started Successfully
                Failed to unmarshall xml. No data read.
                Client 0 connected.
                Client 1 connected.
                26
                1: 10
                0: Terminate connection received
                1: Terminate connection received
                1: Closing connection...
                Expected: 10, Actual: 10
                0: Closing connection...
                Expected: 20, Actual: 26
               */
            //The output for the below is:
            /*
                Starting Server
                Server Started Successfully
                Failed to unmarshall xml. No data read.
                Client 0 connected.
                Client 1 connected.
                26
                0: 10
                1: Terminate connection received
                0: Terminate connection received
                0: Closing connection...
                Expected: 10, Actual: 10
                1: Closing connection...
                Expected: 20, Actual: 26
             */

            //client1.join();
            //client2.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Expected: 10, Actual: " + r1[0]);
        System.out.println("Expected: 20, Actual: " + r2[0]);
    }

    class ClientServerConnection {
        private InetAddress host;
        private static final int PORT = 30572;
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
}
