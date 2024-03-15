package com.mycompany.client;

import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.animation.Animation.Status;
import javafx.scene.layout.GridPane;

/**
 * The button used for add and remove class.
 * It opens a menu for input when the user clicks it playing an animation when it does so.
 */
public class MenuButton extends ReactiveButton {
    private Menu menu;

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

    private boolean courseMenu = false;

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

        //Load in the menu once the animation has ended
        this.openAnim.setOnFinished(event -> {
            if(menu != null)
                this.getChildren().add(menu);
        });

        closeAnim = new ScaleTransition(Duration.millis(500), black);

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
        if(this.courseMenu){
            this.menu = new CourseMenu();
            this.menu.setMinWidth(100);
            this.menu.setMinHeight(75);
            this.menu.setAlignment(Pos.CENTER);

            this.requestFocus();

            this.openAnim.setByX(100 / this.text.getWidth());
            this.openAnim.setByY(75 / this.text.getHeight());
            this.closeAnim.setByX(-100 / this.text.getWidth());
            this.closeAnim.setByY(-75 / this.text.getHeight());
        }
        else{
            this.menu = new InputMenu();
            this.menu.setMinWidth(175);
            this.menu.setMinHeight(215);

            this.openAnim.setByX(175 / this.text.getWidth());
            this.openAnim.setByY(215 / this.text.getHeight());
            this.closeAnim.setByX(-175 / this.text.getWidth());
            this.closeAnim.setByY(-215 / this.text.getHeight());
        }
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

    public void makeCourseMenu(){
        this.courseMenu = true;
    }
}
