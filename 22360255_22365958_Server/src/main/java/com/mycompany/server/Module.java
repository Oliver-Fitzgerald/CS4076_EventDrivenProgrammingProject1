package com.mycompany.server;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Module {
    private LocalDate date;
    private String roomCode;
    private String modCode;
    private String startTime;

    public Module(String date, String roomCode, String modCode, String startTime){
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.roomCode = roomCode;
        this.modCode = modCode;
        this.startTime = startTime;
    }

    public Module(){
        this.date = LocalDate.now();
        this.roomCode = "";
        this.modCode = "";
        this.startTime = "";
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
