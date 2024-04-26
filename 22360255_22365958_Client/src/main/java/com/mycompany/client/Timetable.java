package com.mycompany.client;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.fxml.FXML ;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.zip.ZipEntry;


public class Timetable extends GridPane {
    @FXML public ReactiveButton returnButton ;
    @FXML public GridPane timetable ;

    @FXML
    private Label mondayLabel;
    @FXML
    private Label mondayLabel2;
    @FXML
    private Label mondayLabel3;
    @FXML
    private Label mondayLabel4;
    @FXML
    private Label mondayLabel5;
    @FXML
    private Label mondayLabel6;
    @FXML
    private Label mondayLabel7;
    @FXML
    private Label mondayLabel8;

    @FXML
    private Label tuesdayLabel;
    @FXML
    private Label tuesdayLabel2;
    @FXML
    private Label tuesdayLabel3;
    @FXML
    private Label tuesdayLabel4;
    @FXML
    private Label tuesdayLabel5;
    @FXML
    private Label tuesdayLabel6;
    @FXML
    private Label tuesdayLabel7;
    @FXML
    private Label tuesdayLabel8;

    @FXML
    private Label wednesdayLabel;
    @FXML
    private Label wednesdayLabel2;
    @FXML
    private Label wednesdayLabel3;
    @FXML
    private Label wednesdayLabel4;
    @FXML
    private Label wednesdayLabel5;
    @FXML
    private Label wednesdayLabel6;
    @FXML
    private Label wednesdayLabel7;
    @FXML
    private Label wednesdayLabel8;

    @FXML
    private Label thursdayLabel;
    @FXML
    private Label thursdayLabel2;
    @FXML
    private Label thursdayLabel3;
    @FXML
    private Label thursdayLabel4;
    @FXML
    private Label thursdayLabel5;
    @FXML
    private Label thursdayLabel6;
    @FXML
    private Label thursdayLabel7;
    @FXML
    private Label thursdayLabel8;

    @FXML
    private Label fridayLabel;
    @FXML
    private Label fridayLabel2;
    @FXML
    private Label fridayLabel3;
    @FXML
    private Label fridayLabel4;
    @FXML
    private Label fridayLabel5;
    @FXML
    private Label fridayLabel6;
    @FXML
    private Label fridayLabel7;
    @FXML
    private Label fridayLabel8;
    public Timetable(){
    }

    public void initializeTimetable(String courseDetails){
        returnButton.setText("Return");

        timetable.prefWidthProperty().bind(timetable.sceneProperty().get().widthProperty());
        timetable.prefHeightProperty().bind(timetable.sceneProperty().get().heightProperty());

        GridPane.setHgrow(returnButton, Priority.ALWAYS);
        GridPane.setHalignment(returnButton,HPos.CENTER);
        GridPane.setVgrow(returnButton,Priority.ALWAYS);
        GridPane.setValignment(returnButton, VPos.BOTTOM);

        boolean nextModuleExists = true ;
        courseDetails = ";" + courseDetails ;
        while (nextModuleExists){
            String moduleStartTime = courseDetails.substring(courseDetails.indexOf("at") + 3,courseDetails.indexOf("at") + 5) ;
            String moduleCode = courseDetails.substring(1,courseDetails.indexOf(":")) ;
            String moduleRoom = courseDetails.substring(courseDetails.indexOf(":") + 2,courseDetails.indexOf("at") - 1) ;
            String moduleDate = courseDetails.substring(courseDetails.indexOf("on") + 3,courseDetails.indexOf(";",1)) ;

            String code = "";
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(moduleDate);
                int day = date.getDay() ;
                int time = Integer.parseInt(moduleStartTime) - 8 ;
                code = "" + day + time ;

            }catch (ParseException e){
                System.out.println("Error parsing date of module:" + moduleCode);
                e.printStackTrace();
            }

            setLabel(code,moduleCode,moduleRoom) ;
            courseDetails = courseDetails.substring(courseDetails.indexOf(";",1)) ;
            if (courseDetails.equals(";"))
                nextModuleExists =  false ;
        }



        returnButton.setOnMouseClicked(event -> {
            Client.sceneManager.switchCommandScene(event);
        });


    }

    //This might be a war crime
    void setLabel(String label, String modCode, String room){
        modCode = "Module: " + modCode ;
        room = "Room: " + room ;
        switch (label){
            case "11": mondayLabel.setText(modCode + "\n" + room); break;
            case "12": mondayLabel2.setText(modCode + "\n" + room); break;
            case "13": mondayLabel3.setText(modCode + "\n" + room); break;
            case "14": mondayLabel4.setText(modCode + "\n" + room); break;
            case "15": mondayLabel5.setText(modCode + "\n" + room); break;
            case "16": mondayLabel6.setText(modCode + "\n" + room); break;
            case "17": mondayLabel7.setText(modCode + "\n" + room); break;
            case "18": mondayLabel8.setText(modCode + "\n" + room); break;

            case "21": tuesdayLabel.setText(modCode + "\n" + room); break;
            case "22": tuesdayLabel2.setText(modCode + "\n" + room); break;
            case "23": tuesdayLabel3.setText(modCode + "\n" + room); break;
            case "24": tuesdayLabel4.setText(modCode + "\n" + room);break;
            case "25": tuesdayLabel5.setText(modCode + "\n" + room);break;
            case "26": tuesdayLabel6.setText(modCode + "\n" + room);break;
            case "27": tuesdayLabel7.setText(modCode + "\n" + room);break;
            case "28": tuesdayLabel8.setText(modCode + "\n" + room);break;

            case "31": wednesdayLabel.setText(modCode + "\n" + room);break;
            case "32": wednesdayLabel2.setText(modCode + "\n" + room);break;
            case "33": wednesdayLabel3.setText(modCode + "\n" + room);break;
            case "34": wednesdayLabel4.setText(modCode + "\n" + room);break;
            case "35": wednesdayLabel5.setText(modCode + "\n" + room);break;
            case "36": wednesdayLabel6.setText(modCode + "\n" + room);break;
            case "37": wednesdayLabel7.setText(modCode + "\n" + room);break;
            case "38": wednesdayLabel8.setText(modCode + "\n" + room);break;

            case "41": thursdayLabel.setText(modCode + "\n" + room);break;
            case "42": thursdayLabel2.setText(modCode + "\n" + room);break;
            case "43": thursdayLabel3.setText(modCode + "\n" + room);break;
            case "44": thursdayLabel4.setText(modCode + "\n" + room);break;
            case "45": thursdayLabel5.setText(modCode + "\n" + room);break;
            case "46": thursdayLabel6.setText(modCode + "\n" + room);break;
            case "47": thursdayLabel7.setText(modCode + "\n" + room);break;
            case "48": thursdayLabel8.setText(modCode + "\n" + room);break;

            case "51": fridayLabel.setText(modCode + "\n" + room);break;
            case "52": fridayLabel2.setText(modCode + "\n" + room);break;
            case "53": fridayLabel3.setText(modCode + "\n" + room); break;
            case "54": fridayLabel4.setText(modCode + "\n" + room);break;
            case "55": fridayLabel5.setText(modCode + "\n" + room);break;
            case "56": fridayLabel6.setText(modCode + "\n" + room);break;
            case "57": fridayLabel7.setText(modCode + "\n" + room);break;
            case "58": fridayLabel8.setText(modCode + "\n" + room);break;

        }
    }




}
