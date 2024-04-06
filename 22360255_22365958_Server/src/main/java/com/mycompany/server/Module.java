package com.mycompany.server;

import javax.swing.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@XmlRootElement(name="module")
public class Module {
    private LocalDate date;
    private String roomCode;
    private String modCode;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek day;

    public Module(String date, String roomCode, String modCode, String startTime) throws IncorrectActionException{
        //Check if data is correct before making the module
        String toThrow = "";

        try {
            this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            day = this.date.getDayOfWeek() ;
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

    public Module(){

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

    @Override
    public String toString(){
        return modCode + ": " + roomCode + " at " + startTime + " on " + date;
    }

    @XmlElement(name="date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @XmlElement(name="roomCode")
    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    @XmlElement(name="modCode")
    public String getModCode() {
        return modCode;
    }

    public void setModCode(String modCode) {
        this.modCode = modCode;
    }

    @XmlElement(name="startTime")
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @XmlElement(name="endTime")
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public DayOfWeek getDay(){return day;}
}
