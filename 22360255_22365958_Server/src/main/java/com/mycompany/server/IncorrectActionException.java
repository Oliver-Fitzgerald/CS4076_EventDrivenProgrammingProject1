package com.mycompany.server;

public class IncorrectActionException extends Exception {
    /*
        Error Message will be based on a number of codes:
        (The codes can be composed of one or more numbers. the first digit will usually denote
        what kind of action were dealing with(add, remove, display etc.) with subsequent digits
        signifying what went wrong. If the second digit of a code is 0 then it means the action was
        performed correctly e.g. 00 means the class was displayed correctly. A number of errors
        could possibly occur for each action. For example, add could be missing date information and
        room information. in this case the code would be 113. The following is formatted considering
        indentation to be the subsequent digit.)

        -2 = No information inputted
        -1 = Invalid or unrecognized action code.
        0 = Display Action
            0 = Action performed successfully
            1 = Missing Course Code
        1 = Add
            0 = Action performed successfully
            1 = missing course code
            2 = Incorrect Date
            3 = Incorrect module code
            4 = Incorrect room code
            5 = Incorrect time
            6 = Overlapping Module times(mainly thrown if a module is at the same time of another module in the same course)
            7 = Another class is booked in that room at that time(mainly thrown if a module is at the same time and same room as another module in a different course)
        2 = Remove
            0 = Action performed successfully
            1 = Incorrect Date
            2 = Incorrect module code
            3 = Incorrect room code
            4 = Incorrect time
            5 = Couldn't find the module to remove
        3 = Terminate
            0 = Action performed successfully
     */
    private String errorMessage ;

    public IncorrectActionException(){
        super();
    }
    public IncorrectActionException(String message){
        super(message) ;
    }
}
