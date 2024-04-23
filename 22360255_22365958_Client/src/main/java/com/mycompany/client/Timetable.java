package com.mycompany.client;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.fxml.FXML ;


public class Timetable extends GridPane {
    @FXML public ReactiveButton returnButton ;
    @FXML public GridPane timetable ;
    public Timetable(){
    }

    public void initializeTimetable(){
        returnButton.setText("Return");

        timetable.prefWidthProperty().bind(timetable.sceneProperty().get().widthProperty());
        timetable.prefHeightProperty().bind(timetable.sceneProperty().get().heightProperty());

        GridPane.setHgrow(returnButton, Priority.ALWAYS);
        GridPane.setHalignment(returnButton,HPos.CENTER);
        GridPane.setVgrow(returnButton,Priority.ALWAYS);
        GridPane.setValignment(returnButton, VPos.BOTTOM);

        returnButton.setOnMouseClicked(event -> {
            SceneManager sceneManager = new SceneManager() ;
            sceneManager.switchCommandScene(event);
        });

    }




}
