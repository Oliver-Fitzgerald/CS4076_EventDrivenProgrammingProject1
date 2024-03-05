package com.example.gui_client;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DisplayButton extends GridPane {
    private String[] suggestions = new String[24 * 60];
    private ArrayList<String> classList = new ArrayList<>() ;
    private String[] rooms =  {"KBG1","KBG2","CSG001","CSG002","FB042",} ;

    @FXML
    private Label heading = new Label("Book class time") ;
    @FXML
    private Label enterClass = new Label("Class:") ;
    @FXML
    private Label enterRoom = new Label("Room:") ;
    @FXML
    private Label enterFrom = new Label("From:") ;
    @FXML
    private Label enterTo = new Label("To:") ;
    @FXML
    private Label enterDate = new Label("Date:") ;
    @FXML
    private DatePicker datePicker = new DatePicker() ;
    @FXML
    private ComboBox<String> getClass = new ComboBox<String>() ;
    @FXML
    private ComboBox<String> getRoom = new ComboBox<String>() ;
    @FXML
    private ComboBox<String> getToTime = new ComboBox<String>() ;
    @FXML
    private ComboBox<String> getFromTime = new ComboBox<String>() ;
    @FXML
    private Button submitButton = new Button("Book") ;
    @FXML
    private HBox date = new HBox(enterDate,datePicker) ;
    @FXML
    private HBox classCode = new HBox(enterClass,getClass) ;
    @FXML
    private HBox roomCode = new HBox(enterRoom,getRoom) ;
    @FXML
    private HBox codes = new HBox(classCode,roomCode) ;
    @FXML
    private HBox startTime = new HBox(enterFrom,getToTime) ;
    @FXML
    private HBox endTime = new HBox(enterTo,getFromTime) ;
    @FXML
    private HBox time = new HBox(startTime,endTime) ;
    @FXML
    private VBox details = new VBox(date,codes,time,submitButton) ;
    @FXML
    private Rectangle border = new Rectangle();
    public DisplayButton(){

        //Heading
        heading.setFont(new Font(heading.getFont().getName(),20));
        GridPane.setMargin(heading,new Insets(10));
        GridPane.setHalignment(heading, HPos.CENTER);
        GridPane.setHalignment(details,HPos.CENTER);

        //Prompt labels
        getClass.setPromptText("lm051");
        getRoom.setPromptText("KBG10");
        HBox.setMargin(enterClass,new Insets(0,5,0,0));
        HBox.setMargin(enterDate,new Insets(0,5,0,0));
        HBox.setMargin(enterRoom,new Insets(0,5,0,0));
        HBox.setMargin(enterTo,new Insets(0,5,0,0));
        HBox.setMargin(enterFrom,new Insets(0,5,0,0));


        //time autofill
        getToTime.setPromptText("13:00");
        getToTime.setEditable(true);
        getToTime.getEditor().textProperty().addListener((observable,oldValue,newValue)-> {
            autoFillBox(getToTime ,newValue,suggestions);
        });
        getFromTime.setPromptText("13:00");
        getToTime.setEditable(true);
        getToTime.getEditor().textProperty().addListener((observable,oldValue,newValue)-> {
            autoFillBox(getFromTime ,newValue,suggestions);
        });

        //class and room code autofill
        getRoom.setPromptText("KGB10");
        getRoom.setEditable(true);
        getRoom.getEditor().textProperty().addListener((observable,oldValue,newValue)-> {
            autoFillBox(getRoom ,newValue,suggestions);
        });
        getClass.setPromptText("lm051");
        getClass.setEditable(true);
        getClass.getEditor().textProperty().addListener((observable,oldValue,newValue)-> {
            autoFillBox(getClass ,newValue,suggestions);
        });

        //DATE
        date.setPadding(new Insets(10));
        date.setAlignment(Pos.CENTER);
        //CODES
        classCode.setPadding(new Insets(10));

        roomCode.setPadding(new Insets(10));
        //TIME
        startTime.setPadding(new Insets(10));
        endTime.setPadding(new Insets(10));

        //DETAILS
        this.widthProperty().addListener((observable,oldValue,newValue) -> {
            getClass.setPrefWidth(newValue.doubleValue() / 4);
            getRoom.setPrefWidth(newValue.doubleValue() / 4);
            getToTime.setPrefWidth(newValue.doubleValue() / 4);
            getFromTime.setPrefWidth(newValue.doubleValue() / 4);
        });
        details.setAlignment(Pos.CENTER);

        //adding to parent node
        this.add(details,0,1);
        this.add(heading,0,0);

        //Border
        Rectangle border = new Rectangle();
        border.widthProperty().bind(this.widthProperty());
        border.heightProperty().bind(this.heightProperty());
        border.setStroke(Color.BLACK);
        border.setFill(null);
        border.setStrokeWidth(5);

   }

    private static void autoFillBox(ComboBox box,String newValue ,String[] suggestions){

        if (suggestions[0] == null){
            for (int hour = 0; hour < 24; hour++) {
                for (int minute = 0; minute < 60; minute++) {
                    String time = String.format("%02d:%02d", hour, minute);
                    suggestions[hour * 60 + minute] = time;
                }
            }
        }
        box.getItems().clear();
        String input = newValue ;

        for (String suggestion:suggestions ){
            if (suggestion.length() < 6){
             if (suggestion.substring(0, input.length()).equals(input))
                box.getItems().add(suggestion);
            }
            else if (suggestion.substring(input.indexOf("("),input.indexOf(")")).equals(input.substring(0,input.substring(input.indexOf("("),input.indexOf(")")).length())))
                box.getItems().add(suggestion);

        }
    }
}
