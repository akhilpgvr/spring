package com.aminitiesreservation.managementms.repository;

import com.aminitiesreservation.managementms.model.entity.AmenityEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenitiesRepository extends JpaRepository<AmenityEntity,Long>, JpaSpecificationExecutor<AmenityEntity> {

    public List<AmenityEntity> findAll(Specification<AmenityEntity> spec);
}
