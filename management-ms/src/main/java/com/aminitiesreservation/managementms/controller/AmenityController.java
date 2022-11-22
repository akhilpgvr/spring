package com.aminitiesreservation.managementms.controller;


import com.aminitiesreservation.managementms.enums.AmenitiesEnum;
import com.aminitiesreservation.managementms.enums.CountryCodeEnum;
import com.aminitiesreservation.managementms.enums.GenderEnum;
import com.aminitiesreservation.managementms.enums.TimeEnum;
import com.aminitiesreservation.managementms.exceptions.AgeReservationException;
import com.aminitiesreservation.managementms.exceptions.NameLengthException;
import com.aminitiesreservation.managementms.service.AmenitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/reservation")
public class AmenityController {

    @Autowired
    AmenitiesService amenitiesService;


    @GetMapping("/analysis")
    public ResponseEntity<?> analyseReservation(@RequestHeader String userUuid, @RequestParam AmenitiesEnum userAmenity, @RequestParam Date userDate, @RequestParam TimeEnum userTime){
        return new ResponseEntity(amenitiesService.analyseAmenityReservation(userUuid, userAmenity, userDate, userTime),HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> setReservation(@RequestHeader CountryCodeEnum userCountry, @RequestHeader String userUuid, @RequestParam String userName, @RequestParam AmenitiesEnum userAmenity, @RequestParam Date userDate, @RequestParam TimeEnum userTime, @RequestParam GenderEnum userGender, @RequestParam Integer userAge){
        try {
            return new ResponseEntity(amenitiesService.createAmenityReservation(userCountry, userUuid, userName, userAmenity, userDate, userTime, userGender, userAge), HttpStatus.OK);
        }
        catch (NameLengthException l){
            return new ResponseEntity<>(l.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (AgeReservationException l){
            return new ResponseEntity<>(l.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(Exception l){
            return new ResponseEntity<>(l.getMessage(),HttpStatus.BAD_REQUEST);
        }
        }

    @GetMapping("/retrieve")
    public ResponseEntity<?> getReservation(@RequestHeader String userUuid, @RequestParam Long retrieveId){
        return new ResponseEntity(amenitiesService.getAmenityReservation(userUuid, retrieveId),HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateReservation(@RequestHeader CountryCodeEnum userCountry, @RequestHeader String userUuid, @RequestParam Long userId , @RequestParam String userName, @RequestParam AmenitiesEnum userAmenity, @RequestParam Date userDate, @RequestParam TimeEnum userTime, @RequestParam GenderEnum userGender, @RequestParam Integer userAge){
        try {
            return new ResponseEntity(amenitiesService.updateAmenityReservation(userCountry, userUuid, userId, userName, userAmenity, userDate, userTime, userGender, userAge), HttpStatus.OK);
        }catch(NameLengthException l){
            return new ResponseEntity<>(l.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(Exception l){
            return new ResponseEntity<>(l.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReservation(@RequestHeader String userUuid, @RequestParam Long userId){
        return new ResponseEntity(amenitiesService.deleteAmenityReservation(userUuid, userId),HttpStatus.OK);
    }

}
