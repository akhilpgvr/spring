package com.aminitiesreservation.managementms.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmenitiesUpdateRequest {
    @NotNull
    private Long userId;
    private String userName;
    private Date userDate;
    private String userTime;
    private String userGender;
    private Integer userAge;

}