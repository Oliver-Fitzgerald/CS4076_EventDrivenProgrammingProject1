package com.mycompany.client;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Rectangle2D;

/**
 * Our entry point to the client gui application.
 * This creates the main scene and switches between the start screen and main screen.
 */
public class Client extends Application{
    private final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    private MenuButton addBtn = new MenuButton("Add Class");
    private MenuButton removeBtn = new MenuButton("Remove Class");
    private ReactiveButton displayBtn = new ReactiveButton("Display Class Information");
    private ReactiveButton terminateBtn = new ReactiveButton("Terminate Connection");
    private ClientServerConnection con;

    /**
     * This label is included in order to give the user responsiveness.
     * Things like "connection successful" and "added class succesfully"
     */
    private Label serverResponseLbl = new Label("");


    @Override
    public void start(Stage primaryStage) throws Exception {
        con = new ClientServerConnection();

        Scene commandScene = createCommandScene();
        primaryStage.setScene(commandScene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch();
    }

    /**
     * Creates the main screen scene. It initializes components and sets width and height.
     * @return Scene containing the children for the main menu. Those being HBox's and VBox's as well as the relevant buttons.
     */
    public Scene createCommandScene(){
        addBtn.setOnSubmitEvent(event -> {
            String response = con.send("add:" + event.getEventData());

            this.handleResponseCode(response);
        });

        serverResponseLbl.setId("serverLbl");

        //Here we create the basic layout structure of the scene.
        HBox btnBox = new HBox(addBtn, removeBtn, displayBtn);
        VBox menuBox = new VBox(btnBox, terminateBtn, serverResponseLbl);
        Scene commandScene = new Scene(menuBox, screenBounds.getWidth()/1.8, screenBounds.getHeight()/1.8);

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

        //Adding a stylesheet to the scene
        commandScene.getStylesheets().add(String.valueOf(Client.class.getResource("commandSceneStyleSheet.css")));

        return commandScene;
    }

    private void handleResponseCode(String code){
        if(code.equals("-1")){
            this.serverResponseLbl.setText("Fatal error\nExiting...");
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.exit(1);
            }
            con.terminate();
            System.exit(1);
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
                        errorMessage += "Incorrect date format\n";
                        break;
                    case '2':
                        errorMessage += "Incorrect module code\n";
                        break;
                    case '3':
                        errorMessage += "Incorrect room code\n";
                        break;
                    case '4':
                        errorMessage += "Incorrect time format\n";
                        break;
                    case '5':
                        errorMessage += "Module overlaps with another scheduled module.\n";
                        break;
                }
            }

            this.serverResponseLbl.setText(errorMessage);
        }
    }
}