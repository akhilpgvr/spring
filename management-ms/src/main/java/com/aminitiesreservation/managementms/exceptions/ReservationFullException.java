package com.aminitiesreservation.managementms.exceptions;

public class ReservationFullException extends RuntimeException{

     public ReservationFullException(){
         super("Reservation is full for the selected Amenity");
     }
}
