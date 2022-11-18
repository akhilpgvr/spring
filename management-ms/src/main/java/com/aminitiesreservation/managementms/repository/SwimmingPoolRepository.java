package com.aminitiesreservation.managementms.repository;

import com.aminitiesreservation.managementms.model.entity.SwimmingPoolEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwimmingPoolRepository extends JpaRepository<SwimmingPoolEntity,Long>, JpaSpecificationExecutor<SwimmingPoolEntity> {

    public List<SwimmingPoolEntity> findAll(Specification<SwimmingPoolEntity> spec);
//    int getCount(Date checkDate, String checkTime);
}
