package com.mycompany.client;

import javafx.event.Event;
import javafx.event.EventType;

public class SubmitEvent extends Event{
    public static final EventType<SubmitEvent> SUBMIT_EVENT_TYPE = new EventType<>("SUBMIT");

    public SubmitEvent(){
        super(SUBMIT_EVENT_TYPE);
    }
}
