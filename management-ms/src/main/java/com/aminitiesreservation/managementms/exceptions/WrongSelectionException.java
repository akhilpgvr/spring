package com.aminitiesreservation.managementms.exceptions;

public class WrongSelectionException extends RuntimeException{
    public WrongSelectionException(){
        super("Wrong Selection of Amenity");
    }
}
