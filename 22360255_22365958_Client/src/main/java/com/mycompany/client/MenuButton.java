package com.mycompany.client;

import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.animation.Animation.Status;

/**
 * The button used for add and remove class.
 * It opens a menu for input when the user clicks it playing an animation when it does so.
 */
public class MenuButton extends ReactiveButton {


    private InputMenu menu;

    /**
     * The scale transition of the black background.
     */
    private ScaleTransition openAnim;
    private ScaleTransition closeAnim;

    /**
     * This tracks which of the buttons is currently open.
     */
    private static MenuButton currentButton = null;

    /**
     * This tracks if this button is focused or not.
     */
    private BooleanProperty isOpen = new SimpleBooleanProperty(false);

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
        openAnim.setByX(1.25);
        openAnim.setByY(8);

        //Load in the menu once the animation has ended
        this.openAnim.setOnFinished(event -> {
            if(menu != null)
                this.getChildren().add(menu);
        });

        closeAnim = new ScaleTransition(Duration.millis(500), black);
        closeAnim.setByX(-1.25);
        closeAnim.setByY(-8);

        this.closeAnim.setOnFinished(event -> {
            for(Node child : this.getChildren())
                child.setVisible(true);
        });

        //add event listeners to mouse press and button unfocus to open and close
        //the menu respectively
        this.setOnMouseClicked(event -> clickHandler());
    }

    private void clickHandler(){
        if(!this.isOpen.get()){
            if(currentButton != null){
                currentButton.close();
            }
            this.open();
            this.isOpen.set(true);
            currentButton = this;
        }
    }

    /**
     * Handles the opening of the menu when the button is clicked.
     */
    private void open(){
        this.menu = new InputMenu();
        this.menu.setMinWidth(175);
        this.menu.setMinHeight(215);
        this.menu.submitButton.setOnMouseReleased(event -> close());
        //Stop animation if the close animation is playing
        this.closeAnim.stop();

        //Unload all children of the anchorpane except for the black background
        for(Node child : this.getChildrenUnmodifiable())
            if(child != this.black)
                child.setVisible(false);

        this.openAnim.play();
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
            this.getChildren().remove(menu);
            this.menu = null;
        }

        this.isOpen.set(false);
        currentButton = null;

        this.closeAnim.play();
    }

    public boolean getOpen(){
        return this.isOpen.get();
    }

    public BooleanProperty openProperty(){
        return this.isOpen;
    }

    public void setOnSubmitEvent(EventHandler<SubmitEvent> eventHandler){
        addEventHandler(SubmitEvent.SUBMIT_EVENT_TYPE, eventHandler);
    }
}
