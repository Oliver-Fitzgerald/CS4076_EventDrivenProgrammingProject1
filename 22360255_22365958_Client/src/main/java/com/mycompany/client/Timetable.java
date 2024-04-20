package com.mycompany.client;

import javafx.geometry.HPos;
import javafx.scene.layout.*;
import javafx.fxml.FXML ;

public class Timetable extends GridPane {
    @FXML public ReactiveButton returnButton ;
    public Timetable(){

    }

    public void initializeTimetable(){
        returnButton.setText("Return");
        GridPane.setHgrow(returnButton, Priority.ALWAYS);
        GridPane.setHalignment(returnButton,HPos.CENTER);
    }




}
