package com.aminitiesreservation.managementms.exceptions;

public class NameLengthException extends RuntimeException{
    public NameLengthException(){
        super("Length of name must be between 3 and 20");
    }
}
