package com.aminitiesreservation.managementms.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmenitiesResponse {

    private Long userId;
    private String userName;
    private Date entryDate;
    private String entryTime;
    private String userGender;
    private Integer userAge;

}