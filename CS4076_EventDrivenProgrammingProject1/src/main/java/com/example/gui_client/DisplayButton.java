package com.example.gui_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class DisplayButton extends AnchorPane {
    private String[] suggestions = new String[24 * 60];


    public BorderPane start(){
        BorderPane parent = new BorderPane() ;

        //Heading
        Label heading = new Label("Book class time") ;
        heading.setFont(new Font(heading.getFont().getName(),20));
        BorderPane.setMargin(heading,new Insets(10));
        BorderPane.setAlignment(heading,Pos.CENTER);

        //Prompt labels
        Label enterClass = new Label("Class code") ;
        Label enterRoom = new Label("Room code") ;
        Label fromLabel = new Label("From:") ;
        Label toLabel = new Label("To:") ;
        Label enterDate = new Label("Date") ;

        //Prompt labels
        TextField getClass = new TextField() ;
        getClass.setPromptText("Computer Systems (lm051)");
        TextField getRoom = new TextField() ;
        getRoom.setPromptText("KBG10");
        //Getting time
        ComboBox getToTime = new ComboBox() ;
        getToTime.setEditable(true);
        getToTime.getEditor().textProperty().addListener((observable,oldValue,newValue) -> {
            for (int hour = 0; hour < 24; hour++) {
                for (int minute = 0; minute < 60; minute++) {
                    String time = String.format("%02d:%02d", hour, minute);
                    suggestions[hour * 60 + minute] = time;
                }
            }
            getToTime.getItems().clear();
            String input = newValue ;

            for (String suggestion:suggestions ) {

                if (suggestion.substring(0,input.length()).equals(input))
                    getToTime.getItems().add(suggestion) ;

            }

        });
        getToTime.setPromptText("13:00");
        //From
        ComboBox getFromTime= new ComboBox() ;
        getFromTime.setEditable(true);
        getFromTime.getEditor().textProperty().addListener((observable,oldValue,newValue) -> {
            for (int hour = 0; hour < 24; hour++) {
                for (int minute = 0; minute < 60; minute++) {
                    String time = String.format("%02d:%02d", hour, minute);
                    suggestions[hour * 60 + minute] = time;
                }
            }
            getFromTime.getItems().clear();
            String input = newValue ;

            for (String suggestion:suggestions ) {

                if (suggestion.substring(0,input.length()).equals(input))
                    getFromTime.getItems().add(suggestion) ;

            }

        });
        getFromTime.setPromptText("13:00");
        //Button
        Button submitButton = new Button("Book") ;

        //
        HBox date = new HBox(enterDate) ;
        date.setPadding(new Insets(10));
        HBox classCode = new HBox(enterClass,getClass) ;
        classCode.setPadding(new Insets(10));
        HBox room = new HBox(enterRoom,getRoom) ;
        room.setPadding(new Insets(10));

        HBox startTime = new HBox(fromLabel,getToTime) ;
        startTime.setPadding(new Insets(10));
        HBox endTime = new HBox(toLabel,getFromTime) ;
        endTime.setPadding(new Insets(10));
        HBox time = new HBox(startTime,endTime) ;
        //
        VBox details = new VBox(date,classCode,room,time,submitButton) ;
        details.setAlignment(Pos.CENTER);

        //adding to parent node
        parent.setCenter(details);
        parent.setTop(heading);

        Rectangle border = new Rectangle();
        border.widthProperty().bind(parent.widthProperty());
        border.heightProperty().bind(parent.heightProperty());

        border.setStroke(Color.BLACK);
        border.setFill(null);
        border.setStrokeWidth(5);


        parent.getChildren().add(border) ;

        return parent ;
   }

}
