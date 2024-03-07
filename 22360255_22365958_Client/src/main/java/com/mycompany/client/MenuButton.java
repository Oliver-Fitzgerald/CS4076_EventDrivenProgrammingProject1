package com.mycompany.client;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.animation.Animation.Status;

/**
 * The button used for add and remove class.
 * It opens a menu for input when the user clicks it playing an animation when it does so.
 */
public class MenuButton extends ReactiveButton {
    /**
     * The scale transition of the black background.
     */
    ScaleTransition openAnim;

    /**
     * Constructor for the menu button. It calls the initialization methods.
     * @param text The text which the button should display.
     */
    public MenuButton(String text) {
        super(text);

        initialize();
    }

    /**
     * Initializes the buttons components, specifically the transition and listeners.
     */
    private void initialize(){
        //Set up the scaling transition on the black rectangle
        openAnim = new ScaleTransition(Duration.millis(500), black);
        openAnim.setByX(2);
        openAnim.setByY(2);

        //add event listeners to mouse press and button unfocus to open and close
        //the menu respectively
        this.setOnMouseClicked(event -> open());
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && oldValue){
                close();
            }
        });

    }

    /**
     * Handles the opening of the menu when the button is clicked.
     */
    private void open(){
        //Stop animation if the close animation is playing
        this.openAnim.stop();

        //Unload all children of the anchorpane except for the black background
        for(Node child : this.getChildrenUnmodifiable())
            if(child != this.black)
                this.getChildren().remove(child);

        this.openAnim.setRate(1);
        this.openAnim.play();

        //Add load menu code here*****************************************************************
        InputMenu menu = new InputMenu();
        this.getChildren().clear();
        this.getChildren().add(menu);
    }

    /**
     * Handles the closing of the menu when another button is focused on.
     */
    private void close(){
        //Check if animation is playing. If it is stop it
        //If it isn't then its currently open so we need to unload the menu.
        if (this.openAnim.getStatus().equals(Status.RUNNING))
            this.openAnim.stop();
        else{
            //Add unload menu code here**********************************************************
            this.getChildren().clear();
        }

        this.openAnim.setRate(-1);
        this.openAnim.play();

        this.loadButtonFXML();
    }
}
