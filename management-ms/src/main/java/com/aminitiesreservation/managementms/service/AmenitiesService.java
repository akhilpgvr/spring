package com.aminitiesreservation.managementms.service;

import com.aminitiesreservation.managementms.enums.AmenitiesEnum;
import com.aminitiesreservation.managementms.enums.CountryCodeEnum;
import com.aminitiesreservation.managementms.enums.GenderEnum;
import com.aminitiesreservation.managementms.enums.TimeEnum;
import com.aminitiesreservation.managementms.exceptions.AgeReservationException;
import com.aminitiesreservation.managementms.exceptions.NameLengthException;
import com.aminitiesreservation.managementms.exceptions.ReservationFullException;
import com.aminitiesreservation.managementms.exceptions.WrongSelectionException;
import com.aminitiesreservation.managementms.model.entity.AmenityEntity;
import com.aminitiesreservation.managementms.repository.AmenitiesRepository;
import com.aminitiesreservation.managementms.model.request.AmenitesRequest;
import com.aminitiesreservation.managementms.model.response.AmenitiesResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import static com.aminitiesreservation.managementms.constantValues.AgeRange.*;
import static com.aminitiesreservation.managementms.constantValues.TimeShedules.*;

@Slf4j
@Service
public class AmenitiesService {


    @Autowired
    AmenitiesRepository amenitiesRepository;

    @Autowired
    ModelMapper modelMapper;

    public String analyseAmenityReservation(String userUuid, AmenitiesEnum userAmenity, Date userDate, TimeEnum userTime) {
        String analysisResult = "";
        List<AmenityEntity> amenityEntities = amenitiesRepository.findAll(getReservationQuery(userAmenity.getAmenityType(), userDate,userTime.getUserTime()));
        int reservationSize = amenityEntities.size();
        String[] reservationDetail = getReservationDetail(userAmenity.getAmenityType(),userTime.getUserTime());
        if(reservationSize<Integer.parseInt(reservationDetail[0])){
            analysisResult = "Reservation is Available for Selected Amenity "+"\n"+"Fee for "+userAmenity.getAmenityType()+" is "+ reservationDetail[1];
        }
        else{
            analysisResult = "Reservation Full  for "+userAmenity.getAmenityType();
        }
        log.info("Analysis done for {}",userUuid);
        return analysisResult;

    }

    public String createAmenityReservation(CountryCodeEnum userCountry, String  userUuid, String userName, AmenitiesEnum userAmenity, Date userDate, TimeEnum userTime, GenderEnum userGender, Integer userAge) {
        AmenitesRequest amenitesRequest = new AmenitesRequest();
        amenitesRequest.setUserName(userName);
        amenitesRequest.setUserAmenity(userAmenity);
        amenitesRequest.setUserDate(userDate);
        amenitesRequest.setUserTime(userTime);
        amenitesRequest.setUserGender(userGender);
        amenitesRequest.setUserAge(userAge);
        amenitesRequest.setUserCountry(userCountry);
        amenitesRequest.setUserUuid(userUuid);
        AmenitiesResponse amenitiesResponse = new AmenitiesResponse();
        String checkAmenity = amenitesRequest.getUserAmenity().getAmenityType();
        Date checkDate = amenitesRequest.getUserDate();
        String checkTime = amenitesRequest.getUserTime().getUserTime();
        String[] reservationCheckResult = getReservationDetail(checkAmenity,checkTime);
        int reservationLimit = Integer.parseInt(reservationCheckResult[0]);
        String reservationFee = reservationCheckResult[1];

        if(!nameCheck(amenitesRequest.getUserName())){
            throw new NameLengthException();
        }

        List<AmenityEntity> amenityEntities = amenitiesRepository.findAll(getReservationQuery(checkAmenity, checkDate,checkTime));
        if(amenityEntities.size()<reservationLimit && ageCheck(amenitesRequest.getUserAmenity(),amenitesRequest.getUserAge())){
            AmenityEntity newAmenityEntity = modelMapper.map(amenitesRequest, AmenityEntity.class);
            newAmenityEntity.setUserFee(reservationFee);
            newAmenityEntity.setCreatedPerson(amenitesRequest.getUserName());
            newAmenityEntity.setCreatedDateTime(LocalDateTime.now());
            newAmenityEntity.setLastUpdatedPerson(amenitesRequest.getUserName());
            newAmenityEntity.setLastUpdatedDateTime(LocalDateTime.now());
            amenitiesRepository.save(newAmenityEntity);
            amenitiesResponse = modelMapper.map(newAmenityEntity, AmenitiesResponse.class);
            log.info("Reservation done for {}",userUuid);
        }
        else{
            throw new ReservationFullException();
        }
        return "Reservation created for "+ amenitiesResponse.getUserAmenity() + "\n" +"User ID: "+amenitiesResponse.getUserId() +"\n"+"Fee for "+checkAmenity+" = "+ reservationFee;
    }


    public AmenitiesResponse getAmenityReservation(String userUuid, Long retrieveId) {
        AmenityEntity managementEntity = amenitiesRepository.findById(retrieveId).orElseThrow(()-> new RuntimeException("ID notfound"));
        AmenitiesResponse amenitiesResponse = new AmenitiesResponse();
        amenitiesResponse = modelMapper.map(managementEntity, AmenitiesResponse.class);
        amenitiesResponse.setEntryDate(managementEntity.getUserDate());
        amenitiesResponse.setEntryTime(managementEntity.getUserTime());
        log.info("Reservation passed for {}",userUuid);
        return amenitiesResponse;
    }

    public String deleteAmenityReservation(String userUuid, Long retrieveId) {
        amenitiesRepository.deleteById(retrieveId);
        log.info("Reservation deleted for {}",userUuid);
        return "Swimming pool Reservation with id "+retrieveId+" has deleted";
    }


    public String updateAmenityReservation(CountryCodeEnum userCountry, String userUuid, Long updateUserId, String userName, AmenitiesEnum userAmenity, Date userDate, TimeEnum userTime, GenderEnum userGender, Integer userAge) {
        AmenitiesResponse amenitiesResponse = new AmenitiesResponse();
        AmenityEntity amenityEntity = amenitiesRepository.findById(updateUserId).orElseThrow(() -> new RuntimeException("ID NOT FOUND ERROR"));
        String[] reservationCheckResult = new String[2];
        if (amenityEntity != null) {
            String userCheckAmenity = amenityEntity.getUserAmenity();
            String updateUserName = (StringUtils.isNotBlank(userName) ? userName : amenityEntity.getUserName());
            amenityEntity.setUserName(updateUserName);
            amenityEntity.setUserAmenity(userAmenity.getAmenityType());
            amenityEntity.setUserDate(userDate);
            amenityEntity.setUserTime(userTime.getUserTime());
            amenityEntity.setUserGender(userGender.getUserGender());
            amenityEntity.setUserAge(userAge);
            reservationCheckResult = getReservationDetail(userAmenity.getAmenityType(), userTime.getUserTime());

            if(!nameCheck(updateUserName)){
                throw new NameLengthException();
            }

            List<AmenityEntity> amenityEntities = amenitiesRepository.findAll(getReservationQuery(userAmenity.getAmenityType(), userDate, userTime.getUserTime()));
            if ((((userCheckAmenity).equals(userAmenity.getAmenityType())) || amenityEntities.size() < Integer.parseInt(reservationCheckResult[0]))&&ageCheck(userAmenity,userAge)) {
                AmenityEntity newAmenityEntity = modelMapper.map(amenityEntity, AmenityEntity.class);
                newAmenityEntity.setLastUpdatedPerson(updateUserName);
                newAmenityEntity.setLastUpdatedDateTime(LocalDateTime.now());
                newAmenityEntity.setUserFee(reservationCheckResult[1]);
                amenitiesRepository.save(newAmenityEntity);
                amenitiesResponse = modelMapper.map(newAmenityEntity, AmenitiesResponse.class);
                log.info("Reservation updated for {}",userUuid);
            }
            else {
                throw new ReservationFullException();
            }

        } else {

        }

        return "Reservation updated for "+ amenitiesResponse.getUserAmenity() + "\n" + "Fee for " + userAmenity + " = " + reservationCheckResult[1];
    }


    //Functions

    public Specification<AmenityEntity> getReservationQuery(String checkAmenity, Date checkDate, String checkTime) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("userAmenity"), checkAmenity));
            predicates.add(criteriaBuilder.equal(root.get("userDate"), checkDate));
            predicates.add(criteriaBuilder.equal(root.get("userTime"), checkTime));

            query.orderBy(criteriaBuilder.asc(root.get("userId")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public String[] getReservationDetail(String amenity, String time){
        String[] reservationDetail = new String[2];
        switch(amenity){
            case "GYM":
                reservationDetail[0] = "10";
                if(SHEDULE_ONE.contains(time)){
                    reservationDetail[1] = "30 AED";
                }
                else if(SHEDULE_TWO.contains(time)){
                    reservationDetail[1] = "50 AED";
                }
                break;
            case "SWIMMING POOL":
                reservationDetail[0] = "5";
                if(SHEDULE_ONE.contains(time)){
                    reservationDetail[1] = "20 AED";
                }
                else if(SHEDULE_TWO.contains(time)){
                    reservationDetail[1] = "10 AED";
                }
                break;
            case "STEAM BATH":
                reservationDetail[0]="2";
                if(SHEDULE_ONE.contains(time)){
                    reservationDetail[1] = "25 AED";
                }
                else if(SHEDULE_TWO.contains(time)){
                    reservationDetail[1] = "50 AED";
                }
                break;
            case "YOGA":
                reservationDetail[0] = "3";
                reservationDetail[1] = "50 AED";
                break;
            default:
                throw new WrongSelectionException();
        }
        return reservationDetail;
    }


    public boolean ageCheck(AmenitiesEnum userAmenity, int userAge){
        boolean criteriaResult = false;
        if(userAmenity.equals(AmenitiesEnum.GYM)){
            if(userAge>=MIN_GYM_AGE && userAge<=MAX_GYM_AGE){
                    criteriaResult = true;
                }
            else {
                throw new AgeReservationException();
            }
        }
        else if(userAmenity.equals(AmenitiesEnum.SWIMMING_POOL)){
            if(userAge>=MIN_SWIMINGPOOL_AGE && userAge<=MAX_SWIMINGPOOL_AGE){
                criteriaResult = true;
            }
            else {
                throw new AgeReservationException();
            }
        }
        else if(userAmenity.equals(AmenitiesEnum.STEAM_BATH)){
            if(userAge>=MIN_STEAMBATH_AGE && userAge<=MAX_STEAMBATH_AGE){
                criteriaResult = true;
            }
            else {
                throw new AgeReservationException();
            }
        }
        else if(userAmenity.equals(AmenitiesEnum.YOGA)){
            if(userAge>=MIN_YOGA_AGE && userAge<=MAX_YOGA_AGE){
                criteriaResult = true;
            }
            else {
                throw new AgeReservationException();
            }
        }

        return criteriaResult;
    }


    //User Name Length Checking

    public boolean nameCheck(String userName){
        boolean criteriaResult = false;
        if(userName.length()>=3 && userName.length()<=20){
            criteriaResult =true;
        }
        return criteriaResult;
    }


}

