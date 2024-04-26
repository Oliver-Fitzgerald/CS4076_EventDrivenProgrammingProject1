package com.mycompany.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private Stage stage ;
    private Scene scene ;
    private Parent root ;

    public SceneManager(Stage primaryStage){
        stage = primaryStage;
    }

    public void switchTimetable(String courseDetails) {

        Timetable timetable = new Timetable() ;
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Timetable.fxml"));
                loader.setRoot(timetable);
                root = loader.load();

                timetable = loader.getController();
            }catch (IOException e){
                System.out.println("Error in switch to Timetable");
                e.printStackTrace();
            }

            scene = new Scene(root) ;
            stage.setTitle("Timetable");
            stage.setScene(scene) ;
            stage.show();

            timetable.initializeTimetable(courseDetails) ;


    }
    public void switchCommandScene(MouseEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Client client = new Client() ;

        scene = client.createCommandScene(stage) ;
        stage.setTitle("");
        stage.setScene(scene) ;
        stage.show();
    }
}
