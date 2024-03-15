package com.mycompany.client;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CourseMenu extends Menu{
    private TextField courseCode = new TextField();

    public CourseMenu(){
        this.courseCode.setPromptText("Enter Course");

        VBox vb = new VBox(courseCode, submitButton);
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);

        this.getStylesheets().add(getClass().getResource("imenustyles.css").toExternalForm());
        this.setStyle("-fx-background-color: #111827");

        this.add(vb, 0, 0);

        submitButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            String course = courseCode.getText() == null ? "" : courseCode.getText();

            fireEvent(new SubmitEvent(course));
        });
    }
}
