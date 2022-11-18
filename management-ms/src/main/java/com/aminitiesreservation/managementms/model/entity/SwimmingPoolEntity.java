package com.aminitiesreservation.managementms.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "swimmingpool")
public class SwimmingPoolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", unique = true)
    private Long userId;
    private String userName;
    private Date userDate;
    private String userTime;
    private String userGender;
    private Integer userAge;

}
