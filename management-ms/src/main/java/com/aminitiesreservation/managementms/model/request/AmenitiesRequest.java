package com.aminitiesreservation.managementms.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AmenitiesRequest {
    @NotNull
//    @Size(min = 3, message = "Name must have atleast 3 characters")
    @Size(min = 3, max = 20, message = "Name must not exceed 20 characters")
    private String userName;
    @NotNull
    private Date userDate;
    @NotNull
    private String userTime;
    @NotNull
    private String userGender;
    @NotNull
    @Min(value=12, message = "Minimum age is 12")
    @Max(value=60, message="Maximum age is 60")
    private Integer userAge;

}
