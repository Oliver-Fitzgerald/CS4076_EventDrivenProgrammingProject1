package com.mycompany.client;

import javafx.application.Application;

public class IncorrectActionException extends Exception {
    private String errorMessage ;

    public IncorrectActionException(){
        super();
    }
    public IncorrectActionException(String message){
        super(message) ;
    }
    public String getMessage(){
        if (errorMessage.isEmpty())
            return "This action is incorrect" ;
        else return errorMessage ;
    }
}
