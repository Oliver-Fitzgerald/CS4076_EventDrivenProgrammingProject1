package com.mycompany.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    private static ServerSocket servSock;
    private static final int PORT = 30572;
    private static int numConnections = 0;
    private static boolean loading;
    private static ArrayList<Course> courses = new ArrayList<Course>();

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

    public static String addModule(String data) throws IncorrectActionException{
        int courseIndex = -1;

        //Creating a module from the data
        Module toAdd;
        String courseCode = "";
        String[] splitData = mySplit(data, ',');
        try{
            courseCode = splitData[1];
            toAdd = new Module(splitData[0], splitData[3], splitData[2], splitData[4]);
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("splitData index out of bounds. data: " + Arrays.deepToString(splitData));
            throw new IncorrectActionException("-1");
        } catch(IncorrectActionException e){
            if(courseCode.isEmpty())
                throw new IncorrectActionException("1" + e.getMessage() + "1");
            else
                throw new IncorrectActionException("1" + e.getMessage());
        }

        if(courseCode.isEmpty()){
            throw new IncorrectActionException("11");
        }

        //Check for different types of module overlap
        for(int i = 0; i < courses.size(); i++){
            Course cs = courses.get(i);
            int overlap = cs.overlaps(toAdd);
            if(cs.getCode().equals(courseCode)){
                courseIndex = i;
                if(overlap == 1 || overlap == 2)
                    throw new IncorrectActionException("16");
            }
            else if(overlap == 2){
                throw new IncorrectActionException("17");
            }
        }

        //Adding the module to a course given no overlap
        if(courseIndex != -1)
            courses.get(courseIndex).addModule(toAdd);
        else {
            Course newCourse = new Course(courseCode);
            newCourse.addModule(toAdd);
            courses.add(newCourse);
        }

        //return success code
        return "10";
    }

    public static String removeModule(String data){
        return "";
    }

    private static String[] mySplit(String data, char regex){
        ArrayList<String> out = new ArrayList<>();
        String curString = "";
        for(char ch : data.toCharArray()){
            if(ch == regex) {
                out.add(curString);
                curString = "";
            }
            else
                curString += ch;
        }

        out.add(curString);

        return out.toArray(new String[0]);
    }
}