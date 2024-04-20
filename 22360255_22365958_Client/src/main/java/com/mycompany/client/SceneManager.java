package com.mycompany.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private Stage stage ;
    private Scene scene ;
    private Parent root ;

    public void switchTimetable(SubmitEvent event) {
            stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;

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
            stage.setScene(scene) ;
            stage.show();
            timetable.initializeTimetable() ;

    }
    public void switchCommandScene(SubmitEvent event){
    }
}
