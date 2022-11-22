package com.aminitiesreservation.managementms.model.request;

import com.aminitiesreservation.managementms.enums.AmenitiesEnum;
import com.aminitiesreservation.managementms.enums.CountryCodeEnum;
import com.aminitiesreservation.managementms.enums.GenderEnum;
import com.aminitiesreservation.managementms.enums.TimeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AmenitesRequest {
    private String userName;
    private AmenitiesEnum userAmenity;
    private Date userDate;
    private TimeEnum userTime;
    private GenderEnum userGender;
    private Integer userAge;

    private CountryCodeEnum userCountry;
    private String userUuid;


}
