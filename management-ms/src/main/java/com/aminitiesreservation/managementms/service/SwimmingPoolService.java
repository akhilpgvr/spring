package com.aminitiesreservation.managementms.service;


import com.aminitiesreservation.managementms.model.entity.SwimmingPoolEntity;
import com.aminitiesreservation.managementms.model.request.AmenitiesUpdateRequest;
import com.aminitiesreservation.managementms.repository.SwimmingPoolRepository;
import com.aminitiesreservation.managementms.model.request.AmenitiesRequest;
import com.aminitiesreservation.managementms.model.response.AmenitiesResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SwimmingPoolService {


    @Autowired
    SwimmingPoolRepository swimmingPoolRepository;

    @Autowired
    ModelMapper modelMapper;

    public AmenitiesResponse createSwimmingPoolReservation(AmenitiesRequest amenitiesRequest) {
        Integer len = amenitiesRequest.getUserName().length();
        Integer age = amenitiesRequest.getUserAge();
        AmenitiesResponse amenitiesResponse = new AmenitiesResponse();
        Date checkDate = amenitiesRequest.getUserDate();
        String checkTime = amenitiesRequest.getUserTime();
        List<SwimmingPoolEntity> swimmingPoolEntities = swimmingPoolRepository.findAll(getSwimmingPoolQuery(checkDate,checkTime));
//        if(len>=3 && len<=20){
//            if(age>=12 && age<=60){
//                Date checkDate = amenitiesRequest.getUserDate();
//                String checkTime = amenitiesRequest.getUserTime();
//                int reservationCount = swimmingPoolRepository.getCount(checkDate,checkTime);

                if(swimmingPoolEntities.size()<=5){
                    SwimmingPoolEntity swimmingPoolEntity = modelMapper.map(amenitiesRequest,SwimmingPoolEntity.class);
                    swimmingPoolRepository.save(swimmingPoolEntity);
                    amenitiesResponse = modelMapper.map(swimmingPoolEntity, AmenitiesResponse.class);
                }
                else{

                }
//
//            }
//            else{
//
//            }
//        }
//        else{
//
//        }
        return amenitiesResponse;

    }

    public Specification<SwimmingPoolEntity> getSwimmingPoolQuery(Date checkDate, String checkTime) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(Objects.nonNull(checkDate) && Objects.nonNull(checkTime)){
                predicates.add(criteriaBuilder.equal(root.get("userDate"), checkDate));
            }
            /*if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("email"), request.getEmail()));
            }
            if (request.getName() != null && !request.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")),
                        "%" + request.getName().toLowerCase() + "%"));
            }
            if (request.getGender() != null && !request.getGender().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), request.getGender()));
            }*/
            query.orderBy(criteriaBuilder.desc(root.get("userId")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public AmenitiesResponse getSwimmingPoolReservation(Long retrieveId) {
        SwimmingPoolEntity managementEntity = swimmingPoolRepository.findById(retrieveId).orElseThrow(()-> new RuntimeException("ID notfound"));
        AmenitiesResponse amenitiesResponse = modelMapper.map(managementEntity, AmenitiesResponse.class);
        return amenitiesResponse;
    }

    public String deleteSwimmingPoolReservation(Long retrieveId) {
        swimmingPoolRepository.deleteById(retrieveId);
        return "Swimming pool Reservation with id "+retrieveId+" has deleted";
    }


    public AmenitiesResponse updateSwimmingPoolReservation(Long updateUserId, AmenitiesRequest amenitiesRequest) {
            AmenitiesResponse managementResponse = new AmenitiesResponse();
            SwimmingPoolEntity managementEntity = swimmingPoolRepository.findById(updateUserId).orElseThrow(()->new RuntimeException("ID NOT FOUND ERROR"));
        if (managementEntity != null) {
            String updateUserName = (StringUtils.isNotBlank(amenitiesRequest.getUserName())? amenitiesRequest.getUserName(): managementEntity.getUserName());
            managementEntity.setUserName(updateUserName);
            Date updateUserDate = (StringUtils.isNotBlank(String.valueOf(amenitiesRequest.getUserDate()))) ? (amenitiesRequest.getUserDate()):(managementEntity.getUserDate());
            managementEntity.setUserDate((java.sql.Date) updateUserDate);
            String updateUserTime = (StringUtils.isNotBlank(String.valueOf(amenitiesRequest.getUserTime()))) ? (amenitiesRequest.getUserTime()):(managementEntity.getUserTime());
            managementEntity.setUserTime(updateUserTime);
            String updateUserGender = (StringUtils.isNotBlank(amenitiesRequest.getUserGender())? amenitiesRequest.getUserGender(): managementEntity.getUserGender());
            managementEntity.setUserGender(updateUserGender);
            Integer updateUserAge = (StringUtils.isNotBlank(String.valueOf(amenitiesRequest.getUserAge()))) ? (amenitiesRequest.getUserAge()):(managementEntity.getUserAge());
            managementEntity.setUserAge(updateUserAge);

//            int reservationCount = swimmingPoolRepository.getCount(updateUserDate,updateUserTime);
//            if(reservationCount<=5){
//                swimmingPoolRepository.save(managementEntity);
//                managementResponse = modelMapper.map(managementEntity,ManagementResponse.class);
//            }
//            else{
//
//            }

        }
        else{

        }

            return managementResponse;
    }



}
