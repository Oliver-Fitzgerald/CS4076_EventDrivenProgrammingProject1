package com.mycompany.server;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Module {
    private LocalDate date;
    private String roomCode;
    private String modCode;
    private LocalTime startTime;
    private LocalTime endTime;

    public Module(String date, String roomCode, String modCode, String startTime) throws IncorrectActionException{
        //Check if data is correct before making the module
        String toThrow = "";

        try {
            this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch(DateTimeParseException e){
            toThrow += "2";
        }

        if(roomCode.isEmpty())
            toThrow += "4";
        this.roomCode = roomCode;

        if(modCode.isEmpty())
            toThrow += "3";
        this.modCode = modCode;

        try {
            this.startTime = LocalTime.parse(startTime);
            this.endTime = this.startTime.plusMinutes(50);
        } catch(DateTimeParseException e){
            toThrow += "5";
        }

        if(!toThrow.isEmpty())
            throw new IncorrectActionException(toThrow);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;

        if(obj == null && getClass() != obj.getClass())
            return false;

        Module other = (Module) obj;
        return date.equals(other.getDate()) &&
                roomCode.equals(other.getRoomCode()) &&
                modCode.equals(other.getModCode()) &&
                startTime.equals(other.getStartTime());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getModCode() {
        return modCode;
    }

    public void setModCode(String modCode) {
        this.modCode = modCode;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
