package com.mycompany.client;

import javafx.animation.FillTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;

public class ReactiveButton extends AnchorPane{
    @FXML
    Rectangle background;
    @FXML
    Rectangle slider;
    @FXML
    Label text;
    @FXML
    AnchorPane pane;
    FillTransition fillTransition;
    FillTransition clearTransition;
    boolean transitioning = false;
    DropShadow ds = new DropShadow();

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

    public void initialize(String text){
        fillTransition = new FillTransition(Duration.millis(300), background, Color.WHITE, Color.BLACK);
        clearTransition = new FillTransition(Duration.millis(300), background, Color.BLACK, Color.WHITE);

        this.text.setText(text);

        ds.setBlurType(BlurType.THREE_PASS_BOX);
        ds.setColor(Color.BLACK);
        ds.setRadius(1);
        ds.setSpread(1);
        ds.setOffsetX(2);
        ds.setOffsetY(2);

        this.setOnMouseEntered(event -> enterTransition());
        this.setOnMouseExited(event -> exitTransition());

        this.background.widthProperty().bind(this.text.widthProperty());
        this.background.heightProperty().bind(this.text.heightProperty());
        this.background.setEffect(ds);

        this.setMaxHeight(this.background.getHeight());

        this.slider.widthProperty().bind(this.background.widthProperty());
        this.slider.heightProperty().bind(this.background.heightProperty());

        this.slider.setX(100);
    }

    public void enterTransition(){
        this.background.setEffect(null);
        text.setTextFill(Color.WHITE);
        fillTransition.play();
    }

    public void exitTransition(){
        clearTransition.play();
        text.setTextFill(Color.BLACK);
        background.setEffect(ds);
    }
}