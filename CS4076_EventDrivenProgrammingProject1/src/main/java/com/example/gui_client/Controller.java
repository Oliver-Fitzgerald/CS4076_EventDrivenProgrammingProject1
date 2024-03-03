package com.example.gui_client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Controller {
    @FXML
    private Button addClass = new Button("Add Class") ;
    @FXML
    private Button removeButton = new Button("Remove Class") ;
    @FXML
    private Button displayButton = new Button("Display Class Schedule") ;
    @FXML
    private Button terminateButton = new Button("Terminate") ;
    @FXML
    private VBox vBox = new VBox() ;
}