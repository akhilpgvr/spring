package com.aminitiesreservation.managementms.model.entity;

import com.aminitiesreservation.managementms.enums.AmenitiesEnum;
import com.aminitiesreservation.managementms.enums.CountryCodeEnum;
import com.aminitiesreservation.managementms.enums.GenderEnum;
import com.aminitiesreservation.managementms.enums.TimeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "amenitiesreservation")
public class AmenityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", unique = true)
    private Long userId;
    private String userName;
//    @Enumerated(EnumType.STRING)
    private String userAmenity;
    private Date userDate;
//    @Enumerated(EnumType.STRING)
    private String userTime;
//    @Enumerated(EnumType.STRING)
    private String userGender;
    private Integer userAge;
    private String userFee;

    private String createdPerson;
    private LocalDateTime createdDateTime;
    private String lastUpdatedPerson;
    private LocalDateTime lastUpdatedDateTime;
    @Enumerated(EnumType.STRING)
    private CountryCodeEnum userCountry;
    private String userUuid;


}
