package com.mycompany.client;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public class MenuButton extends ReactiveButton {
    ScaleTransition openAnim;



    public MenuButton(String text) {
        super(text);

        initialize();
    }

    private void initialize(){
        openAnim = new ScaleTransition(Duration.millis(500), black);
        openAnim.setByX(2);
        openAnim.setByY(2);

        this.setOnMouseClicked(event -> open());
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && oldValue){
                close();
            }
        });

    }

    private void open(){

    }

    private void close(){

    }
}
