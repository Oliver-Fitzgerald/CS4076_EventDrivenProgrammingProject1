package com.mycompany.client;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class InputMenu extends GridPane {
    private String[] suggestions = new String[24 * 4];
    private String[] classList = {"Computer Systems(lm051)","Philosophy (mf041)","Mechanical Engineering (lm060)","Arts (mf042)"};
    private String[] rooms =  {"Kemmy Business School G01","Kemmy Business School G02","Computer Science G001","Computer Science G002","Foundation Building 042",} ;
    private Label heading = new Label("Module Data");
    private String userInput ;
    private DatePicker datePicker = new DatePicker() ;
    private TextField getClass = new TextField() ;
    private TextField getRoom = new TextField() ;
    private ComboBox<String> getTime = new ComboBox<String>() ;
    public ReactiveButton submitButton = new ReactiveButton("Book") ;
    private HBox codes = new HBox(getClass, getRoom) ;
    private VBox details = new VBox(datePicker, codes, getTime, submitButton) ;

    public InputMenu(){

        this.datePicker.setPromptText("Enter Date");
        this.getClass.setPromptText("Enter Course");
        this.getRoom.setPromptText("Enter Room");
        this.getTime.setPromptText("Enter Start Time");

        this.codes.setSpacing(10);
        this.details.spacingProperty().bind(Bindings.multiply(this.widthProperty(),0.09));
        this.details.setPadding(new Insets(5));

        this.heading.setId("heading");

        this.getStylesheets().add(getClass().getResource("imenustyles.css").toExternalForm());
        this.setStyle("-fx-background-color: #111827");

        //Heading
        heading.setFont(new Font(heading.getFont().getName(),20));
        GridPane.setMargin(heading,new Insets(10));
        GridPane.setHalignment(heading, HPos.CENTER);
        GridPane.setHalignment(details,HPos.CENTER);

        getTime.setEditable(true);
        getTime.getEditor().textProperty().addListener((observable,oldValue,newValue)-> {
            autoFillBox(getTime ,newValue,suggestions);
        });

        details.setAlignment(Pos.CENTER);

        //adding to parent node
        this.add(details,0,1);
        this.add(heading,0,0);

        //Book button
        userInput = "" ;
        submitButton.setOnMouseClicked(event -> {
            userInput += "Date:" + datePicker.getValue().toString() ;
            userInput += ",ClassCode:" + getClass.getText();
            userInput += ",RoomCode:" + getRoom.getText();
            userInput += ",From:" + getTime.getValue();

            //client.send("add" + userInput) ;
        });


   }

    private static void autoFillBox(ComboBox box,String newValue ,String[] suggestions){
        box.getItems().clear();
        String input = newValue ;

        for (String suggestion:suggestions ){
             if (suggestion.substring(0, input.length()).equals(input))
                box.getItems().add(suggestion);
        }
    }
    /**
     *Fills the suggestions array with times incrementing by 15mins
     * @Param suggestions String[]
    */
    private static String[] fillTimeArray(String[] suggestions){
        int number = 0;

        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 4; minute++) {
                String time = String.format("%02d:%02d", hour, minute * 15);
                suggestions[minute + (hour * 4)] = time;
            }
        }
        return suggestions ;
    }
}
