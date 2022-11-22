package com.aminitiesreservation.managementms.exceptions;

public class AgeReservationException extends RuntimeException{
    public AgeReservationException(){
        super("Age is not Supported for Selected Entity");
    }
}
