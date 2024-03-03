package com.example.gui_client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenu extends Application {
    public static void main(String args[]){
        launch(args);
    }
    @Override
    public void start(Stage stage) {

        stage.setTitle("Class Scheduler");

        //Labels
        Label heading = new Label("Main Menu") ;
        heading.setFont(new Font(heading.getFont().getName(),30));

        //Buttons
        Button addButton = new Button("Add Class");
        Button removeButton = new Button("Remove Class");
        Button displayButton = new Button("Display Class Schedule");
        Button terminateButton = new Button("Terminate");
        //Button box
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(addButton,displayButton,removeButton) ;

        //Creating parent Node
        BorderPane parent = new BorderPane();

        //setting position of heading
        BorderPane.setAlignment(heading, Pos.CENTER);
        BorderPane.setMargin(heading,new Insets(20));
        parent.setTop(heading);

        //adding terminate button
        BorderPane.setMargin(terminateButton,new Insets(50));
        BorderPane.setAlignment(terminateButton,Pos.CENTER);
        parent.setBottom(terminateButton);

        //adding button box to scene
        BorderPane.setAlignment(buttonBox,Pos.CENTER);
        parent.setCenter(buttonBox);

        //adding Parent Node to scene and setting the scene
        Scene scene = new Scene(parent,750,500) ;
        stage.setScene(scene);
        stage.show();
    }
}