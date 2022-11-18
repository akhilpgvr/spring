package com.aminitiesreservation.managementms.controller;


import com.aminitiesreservation.managementms.model.request.AmenitiesRequest;
import com.aminitiesreservation.managementms.model.request.AmenitiesUpdateRequest;
import com.aminitiesreservation.managementms.service.SwimmingPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservation")
public class SwimmingPoolController {

    @Autowired
    SwimmingPoolService swimmingPoolService;

    @PostMapping("/create")
    public ResponseEntity<?> setReservation( @Valid AmenitiesRequest managementRequest){
        return new ResponseEntity(swimmingPoolService.createSwimmingPoolReservation(managementRequest), HttpStatus.OK);
    }

    @GetMapping("/retrieve")
    public ResponseEntity<?> getReservation(@RequestParam Long retrieveId){
        return new ResponseEntity(swimmingPoolService.getSwimmingPoolReservation(retrieveId),HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateReservation(@RequestParam Long updateUserId , @Valid AmenitiesRequest amenitiesRequest ){
        return new ResponseEntity(swimmingPoolService.updateSwimmingPoolReservation(updateUserId,amenitiesRequest),HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReservation(@RequestParam Long retrieveId){
        return new ResponseEntity(swimmingPoolService.deleteSwimmingPoolReservation(retrieveId),HttpStatus.OK);
    }

}
