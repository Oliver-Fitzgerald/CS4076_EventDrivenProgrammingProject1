package com.mycompany.client;

import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.time.format.DateTimeFormatter;

public class InputMenu extends GridPane {
    private String[] suggestions = new String[24 * 4];
    private Label heading = new Label("Module Data");
    private String userInput = "";
    private DatePicker datePicker = new DatePicker() ;
    private TextField roomCode = new TextField() ;
    private TextField courseCode = new TextField() ;
    private TextField moduleCode = new TextField() ;
    private ComboBox<String> getTime = new ComboBox<String>() ;
    public ReactiveButton submitButton = new ReactiveButton("Submit") ;
    private HBox codes = new HBox(courseCode, moduleCode) ;
    private VBox details = new VBox(datePicker,roomCode, codes, getTime, submitButton) ;

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InputMenu(){

        this.datePicker.setPromptText("Enter Date");
        this.roomCode.setPromptText("Enter Room");
        this.courseCode.setPromptText("Enter Course");
        this.moduleCode.setPromptText("Enter Module");
        this.getTime.setPromptText("Enter Start Time");

        this.codes.setSpacing(10);
        this.details.setSpacing(8); ;
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
        submitButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            String date = datePicker.getValue() == null ? "" : datePicker.getValue().format(format).toString();
            String course = courseCode.getText() == null ? "" : courseCode.getText();
            String module = moduleCode.getText() == null ? "" : moduleCode.getText();
            String room = roomCode.getText() == null ? "" : roomCode.getText() ;
            String time = getTime.getValue() == null ? "" : getTime.getValue().toString();
            userInput = String.format("%s,%s,%s,%s,%s", date, course, module, room, time);

            fireEvent(new SubmitEvent(userInput));
        });
   }

    private static void autoFillBox(ComboBox box,String newValue ,String[] suggestions){
        box.getItems().clear();
        fillTimeArray(suggestions) ;
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
