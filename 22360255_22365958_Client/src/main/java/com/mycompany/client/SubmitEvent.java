package com.mycompany.client;

import javafx.event.Event;
import javafx.event.EventType;

public class SubmitEvent extends Event{
    public static final EventType<SubmitEvent> SUBMIT_EVENT_TYPE = new EventType<>(Event.ANY, "SUBMIT_EVENT");
    private String eventData;

    public SubmitEvent(String eventData){
        super(SUBMIT_EVENT_TYPE);
        this.eventData = eventData;
    }

    public String getEventData(){
        return eventData;
    }

    @Override
    public EventType<? extends Event> getEventType(){
        return SUBMIT_EVENT_TYPE;
    }
}
