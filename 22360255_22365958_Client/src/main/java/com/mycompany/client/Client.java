package com.mycompany.client;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.WeakChangeListener;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Our entry point to the client gui application.
 * This creates the main scene and switches between the start screen and main screen.
 */
public class Client extends Application{
    private final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    private MenuButton addBtn = new MenuButton("Add Class");
    private MenuButton removeBtn = new MenuButton("Remove Class");
    private MenuButton displayBtn = new MenuButton("Display Class Information");
    private ReactiveButton terminateBtn = new ReactiveButton("Terminate Connection") ;
    private static ClientServerConnection con ;
    protected static SceneManager sceneManager;
    public static boolean connected = false ;
    public static boolean earlyMorning = false ;
    /**
     * This label is included in order to give the user responsiveness.
     * Things like "connection successful" and "added class succesfully"
     */
    private Label serverResponseLbl = new Label("");


    @Override
    public void start(Stage primaryStage) throws Exception {
        sceneManager = new SceneManager(primaryStage);

        ConnectScreen connect = new ConnectScreen() ;

        connect.connectButton.setOnMouseClicked(event -> {
            con = new ClientServerConnection();

            primaryStage.setOnCloseRequest(e -> this.handleResponseCode(con.send("ter:---")));

            Scene commandScene = createCommandScene(primaryStage);
            primaryStage.setScene(commandScene);
            this.displayMsg("Connected Succesfully");
        });

        Scene intialScene = new Scene(connect, screenBounds.getWidth() / 1.6, screenBounds.getHeight() / 1.6);

        primaryStage.setTitle("Class scheduler");
        primaryStage.setScene(intialScene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch();
    }

    /**
     * Creates the main screen scene. It initializes components and sets width and height.
     * @return Scene containing the children for the main menu. Those being HBox's and VBox's as well as the relevant buttons.
     */
    public Scene createCommandScene(Stage primaryStage){
        addBtn.setOnSubmitEvent(event -> {
            Task<String> task = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return con.send("add:" + event.getEventData());
                }
            };

            task.setOnSucceeded(e -> {
                this.handleResponseCode(task.getValue());
            });

            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        });

        removeBtn.setOnSubmitEvent(event -> {
            Task<String> task = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return con.send("rem:" + event.getEventData());
                }
            };

            task.setOnSucceeded(e -> {
                this.handleResponseCode(task.getValue());
            });

            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        });

        displayBtn.makeCourseMenu();
        displayBtn.setOnSubmitEvent(event -> {
            Task<String> task = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return con.send("dis:" + event.getEventData());
                }
            };

            task.setOnSucceeded(e -> {
                this.handleResponseCode(task.getValue());
            });

            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        });

        terminateBtn.setOnMouseClicked(event -> {
            Task<String> task = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return con.send("ter:---");
                }
            };

            task.setOnSucceeded(e -> {
                this.handleResponseCode(task.getValue());
            });

            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        });


        CheckBox earlyMorning = new CheckBox("Early Morning") ;
        earlyMorning.setOnAction(event -> {
            String response = con.send("ear:") ;
            Client.earlyMorning = !Client.earlyMorning ;
        });
        if (Client.earlyMorning){
            earlyMorning.setSelected(true);
        }

        serverResponseLbl.setId("serverLbl");

        //Here we create the basic layout structure of the scene.
        HBox btnBox = new HBox(addBtn, removeBtn, displayBtn);
        VBox menuBox = new VBox(btnBox, terminateBtn, earlyMorning, serverResponseLbl);
        Scene commandScene = new Scene(menuBox, screenBounds.getWidth()/1.6, screenBounds.getHeight()/1.6);

        //As I've learned spacing should be done in the java not the css
        //due to bindings(can't have percentages in javafx css, as opposed to normal)
        //You may ask yourself why I picked these values?
        //Very precise and arduous research
        menuBox.prefWidthProperty().bind(commandScene.widthProperty());
        menuBox.prefHeightProperty().bind(commandScene.heightProperty());
        menuBox.spacingProperty().bind(Bindings.divide(menuBox.prefHeightProperty(), 6));

        btnBox.prefWidthProperty().bind(Bindings.divide(menuBox.prefWidthProperty(), 1.5));
        btnBox.prefHeightProperty().bind(Bindings.divide(menuBox.prefHeightProperty(), 3));
        btnBox.spacingProperty().bind(Bindings.divide(btnBox.prefWidthProperty(), 5));

        addBtn.minWidthProperty().bind(Bindings.divide(btnBox.prefWidthProperty(), 4));
        removeBtn.minWidthProperty().bind(Bindings.divide(btnBox.prefWidthProperty(), 4));
        displayBtn.minWidthProperty().bind(Bindings.divide(btnBox.prefWidthProperty(), 4));

        //Adding a stylesheet to the scene
        commandScene.getStylesheets().add(String.valueOf(Client.class.getResource("commandSceneStyleSheet.css")));

        return commandScene;
    }

    private void handleResponseCode(String code){
        if(code.equals("-1")){
            this.displayMsg("Fatal error\nExiting...");
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.exit(1);
            }
            con.terminate();
            System.exit(1);
        }
        else if(code.equals("-2")){
            this.displayMsg("No data entered");
        }
        else if(code.charAt(0) == '1'){
            char[] errors = code.substring(1).toCharArray();
            String errorMessage = "";

            for(char ch : errors){
                switch(ch){
                    case '0':
                        errorMessage += "Succesfully added module";
                        break;
                    case '1':
                        errorMessage += "Missing course code\n";
                        break;
                    case '2':
                        errorMessage += "Incorrect/Missing date\n";
                        break;
                    case '3':
                        errorMessage += "Incorrect/Missing module code\n";
                        break;
                    case '4':
                        errorMessage += "Incorrect/Missing room code\n";
                        break;
                    case '5':
                        errorMessage += "Incorrect/Missing time format\n";
                        break;
                    case '6':
                    case '7':
                        errorMessage += "Module overlaps with another scheduled module.\n";
                        break;
                }
            }

            this.displayMsg(errorMessage);
        }
        else if(code.charAt(0) == '2'){
            char[] errors = code.substring(1).toCharArray();
            String errorMessage = "";

            for(char ch : errors) {
                switch (ch) {
                    case '0':
                        errorMessage += "Succesfully removed module";
                        break;
                    case '1':
                        errorMessage += "Missing course code\n";
                        break;
                    case '2':
                        errorMessage += "Incorrect/Missing date\n";
                        break;
                    case '3':
                        errorMessage += "Incorrect/Missing module code\n";
                        break;
                    case '4':
                        errorMessage += "Incorrect/Missing room code\n";
                        break;
                    case '5':
                        errorMessage += "Incorrect/Missing time format\n";
                        break;
                    case '6':
                        errorMessage += "Module to remove not found\n";
                        break;
                }
            }

            this.displayMsg(errorMessage);
        }
        else if(code.charAt(0) == '0'){
            switch(code.charAt(1)){
                case '0':
                    sceneManager.switchTimetable(code.split("\\|")[1]);
                    this.displayMsg("Succesfully displayed course");
                    break;
                case '1':
                    this.displayMsg("Missing course code");
                    break;
                case '2':
                    this.displayMsg("Course to display not found");
                    break;
            }
        }
        else if(code.charAt(0) == '3'){
            closeApp();
        }
    }

    private void displayMsg(String msg){
        //Resizing label font size depending on how big the error message is
        if(msg.length() > 120)
            this.serverResponseLbl.setStyle("-fx-font-size: 12;");
        else if(msg.length() > 75)
            this.serverResponseLbl.setStyle("-fx-font-size: 18;");
        else
            this.serverResponseLbl.setStyle("-fx-font-size: 24;");

        serverResponseLbl.setText(msg);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), serverResponseLbl);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        pause.setOnFinished(e -> fadeOut.play());
        fadeOut.setOnFinished(e -> {
            this.serverResponseLbl.setText("");
            this.serverResponseLbl.setOpacity(1.0);
        });

        pause.play();
    }

    private void closeApp(){
        this.serverResponseLbl.setStyle("-fx-font-size: 24;");
        this.displayMsg("Closing connection & shutting off");

        con.terminate();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            System.exit(0);
        }));
        timeline.play();
    }
}