package com.mycompany.client;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Custom button component for the class scheduler.
 * This class in particular is responsible for styling and mouse hover effects.
 */
public class ReactiveButton extends StackPane {
    /**
     * The initial background of the button pre-hover. This is what becomes minimized on hover.
     */
    @FXML
    Rectangle white;
    /**
     * The background of the button after hover. This component is also what ultimately enlarges for the onclick animation.
     */
    @FXML
    Rectangle black;
    /**
     * This is a remnant from the creations of the old button. I believe the white background is still clipped to this,
     * however it doesn't really matter as the white background will always exist within the space of the clip.
     * Also, a clip is something where an element won't display outside the bounds of a specified clip.
     * So the line white.setClip(clip) means that the white button will only display within the bounds of the clip rectangle(supposedly)
     */
    Rectangle clip;
    @FXML
    Label text;

    /**
     * Scale transition of the white background on hover.
     */
    ScaleTransition slide;

    /**
     * Text fill animation that goes from black to white on hover.
     */
    Timeline textFill;

    /**
     * Constructor initializing the button.
     * @param text The text to be displayed.
     */
    public ReactiveButton(String text){
        //Here we load the button's fxml
        this.loadButtonFXML();

        //Here we initalize width, height, listeners, transitions, etc.
        this.initialize(text);
    }

    public ReactiveButton(){
        this("");
    }

    /**
     * Loads the button's fxml and sets the parsed nodes to this(the parent node being an anchorpane)
     * @throws RuntimeException When the fxml file fails to load.
     */
    protected void loadButtonFXML() throws RuntimeException{
        //Loads the fxml document.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReactiveButton.fxml"));

        //Sets the root of this object to the loader(essentially making the anchorpane root specified in the fxml this)
        loader.setRoot(this);

        //Sets the nodes' controller to this class
        loader.setController(ReactiveButton.this);

        //Attempts to load the elements outlined in the xml.
        //If it fails to load we convert it from an IOException to a RuntimeException to be handled accordingly later
        try {
            loader.load();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes the components of the button including setting width, height, position, event handlers and so on.
     * @param text The button's text string.
     */
    public void initialize(String text){
        //We set the Label's text initially as whatever the size the text is will be the size of the button
        this.text.setText(text);

        //Binds the black background's width and height to that of the label's from here on everything will
        //be based on the black backgrounds width and height as it is the first child of the stackpane.
        this.black.widthProperty().bind(this.text.widthProperty());
        this.black.heightProperty().bind(this.text.heightProperty());

        //Here we generate the clip for the white background based on the black backgrounds
        //width, height and position.
        this.clip = new Rectangle(0, 0, this.black.getWidth(), this.black.getHeight());
        this.clip.widthProperty().bind(this.black.widthProperty());
        this.clip.heightProperty().bind(this.black.heightProperty());
        this.clip.xProperty().bind(this.black.xProperty());
        this.clip.yProperty().bind(this.black.yProperty());

        //Sets the white backgrounds width, height and clip.
        this.white.widthProperty().bind(this.black.widthProperty());
        this.white.heightProperty().bind(this.black.heightProperty());
        this.white.setClip(this.clip);

        //Creates the scale transition
        this.slide = new ScaleTransition(Duration.millis(300), white);
        this.slide.setFromX(1);
        this.slide.setToX(0);
        this.slide.setFromY(1);
        this.slide.setToY(0);

        //Creates the text fill animation from black to white.
        this.textFill = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(this.text.textFillProperty(), Color.BLACK)),
                                     new KeyFrame(Duration.millis(300), new KeyValue(this.text.textFillProperty(), Color.WHITE)));

        //Binds the stackpane's height to the black background's height.
        this.maxHeightProperty().bind(this.black.heightProperty());

        //Sets onMouseEntered and onMouseExited listeners to the event handlers.
        this.setOnMouseEntered(event -> enterEvent());
        this.setOnMouseExited(event -> exitEvent());
    }

    /**
     * The event handler for hovering the button
     */
    private void enterEvent(){
        //Stops the button's animation if it is transitioning while entering it
        //(consider if the user stops hovering then immediately starts hovering)
        this.slide.stop();
        this.textFill.stop();

        //Hard to explain. Instead of animation playing backwards it plays forwards.
        //We do this because the exit event handler makes the animations play backwards(for obvious reasons).
        this.slide.setRate(1);
        this.textFill.setRate(1);

        //Plays the animations
        this.textFill.play();
        this.slide.play();
    }

    /**
     * Event handler for unhovering the button.
     */
    private void exitEvent(){
        //Stops the button's animation if it is transitioning while exiting it
        //(consider if the user starts hovering then immediately stops hovering)
        this.slide.stop();
        this.textFill.stop();

        //Hard to explain. Instead of animation playing forwards it plays backwards.
        //We do this because the enter event handler makes the animations play forwards(for obvious reasons).
        this.slide.setRate(-1);
        this.textFill.setRate(-1);

        //Plays the animations
        this.textFill.play();
        this.slide.play();
    }
}