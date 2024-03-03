package com.example.gui_client;

import com.almasb.fxgl.localization.Language;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class DisplayButton extends Application {
    private String[] suggestions = new String[24 * 60];


    @Override
    public void start(Stage stage) throws IOException {
        //Parent Node
        BorderPane parent = new BorderPane() ;
        Scene scene = new Scene(parent,750,500) ;

        //Heading
        Label heading = new Label("Book class time") ;
        heading.setFont(new Font(heading.getFont().getName(),30));
        parent.set

        //prompt Class details box
        VBox labels = new VBox() ;
        //Prompt labels
        Label enterClass = new Label("Class code") ;
        Label enterRoom = new Label("Room code") ;
        Label fromLabel = new Label("From:") ;
        Label toLabel = new Label("To:") ;
        Label enterDay = new Label("Day") ;
        labels.getChildren().addAll(enterClass,enterRoom,fromLabel,toLabel,enterDay) ;

        //Enter class details
        VBox textFields = new VBox() ;
        //Prompt labels
        TextField getClass = new TextField() ;
        getClass.setPromptText("Computer Systems (lm051)");
        TextField getRoom = new TextField() ;
        getRoom.setPromptText("KBG10");
        //Getting time
        ComboBox getTime = new ComboBox() ;
        getTime.setEditable(true);
        getTime.getEditor().textProperty().addListener((observable,oldValue,newValue) -> {
            for (int hour = 0; hour < 24; hour++) {
                for (int minute = 0; minute < 60; minute++) {
                    String time = String.format("%02d:%02d", hour, minute);
                    suggestions[hour * 60 + minute] = time;
                }
            }
            getTime.getItems().clear();
            String input = newValue ;

            for (String suggestion:suggestions ) {

                if (suggestion.substring(0,input.length()).equals(input))
                    getTime.getItems().add(suggestion) ;

            }

        });
        getTime.setPromptText("13:00");
        TextField getDay = new TextField() ;
        getDay.setPromptText("monday");

        textFields.getChildren().addAll(getClass, getRoom, getTime, getDay) ;

        //Button
        Button submitButton = new Button("Book") ;

        //adding to parent node
        HBox centre = new HBox(10) ;
        centre.getChildren().addAll(labels,textFields) ;
        parent.setCenter(centre);
        parent.setTop(heading);
        //setting the scene
        stage.setTitle("Class Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
