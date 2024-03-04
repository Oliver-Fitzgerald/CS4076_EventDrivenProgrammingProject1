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

public class ReactiveButton extends StackPane {
    @FXML
    Rectangle white;
    @FXML
    Rectangle black;
    Rectangle clip;
    @FXML
    Label text;
    ScaleTransition slide;
    Timeline textFill;

    public ReactiveButton(String text){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReactiveButton.fxml"));
        loader.setRoot(this);
        loader.setController(ReactiveButton.this);

        try {
            loader.load();
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        initialize(text);
    }

    public ReactiveButton(){
        this("");
    }

    public void initialize(String text){
        this.text.setText(text);

        this.black.widthProperty().bind(this.text.widthProperty());
        this.black.heightProperty().bind(this.text.heightProperty());

        this.clip = new Rectangle(0, 0, this.black.getWidth(), this.black.getHeight());
        this.clip.widthProperty().bind(this.black.widthProperty());
        this.clip.heightProperty().bind(this.black.heightProperty());
        this.clip.xProperty().bind(this.black.xProperty());
        this.clip.yProperty().bind(this.black.yProperty());

        this.white.widthProperty().bind(this.black.widthProperty());
        this.white.heightProperty().bind(this.black.heightProperty());
        this.white.setClip(this.clip);

        this.slide = new ScaleTransition(Duration.millis(300), white);
        this.slide.setFromX(1);
        this.slide.setToX(0);
        this.slide.setFromY(1);
        this.slide.setToY(0);

        this.textFill = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(this.text.textFillProperty(), Color.BLACK)),
                                     new KeyFrame(Duration.millis(300), new KeyValue(this.text.textFillProperty(), Color.WHITE)));

        this.maxHeightProperty().bind(this.black.heightProperty());

        this.setOnMouseEntered(event -> enterEvent());
        this.setOnMouseExited(event -> exitEvent());
    }

    private void enterEvent(){
        //Make fill transition for text
        this.slide.stop();
        this.textFill.stop();

        this.slide.setRate(1);
        this.textFill.setRate(1);

        this.textFill.play();
        this.slide.play();
    }

    private void exitEvent(){
        this.slide.stop();
        this.textFill.stop();

        this.slide.setRate(-1);
        this.textFill.setRate(-1);

        this.textFill.play();
        this.slide.play();
    }
}