package com.mindgeek.samplecode.repository;

import com.mindgeek.samplecode.domainobject.CarDO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CustomCarRepositoryImpl implements CustomCarRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CarDO> getCarByCustomAttributes(CarDO carDO) {

        if(null != carDO){
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<CarDO> criteriaQuery = criteriaBuilder.createQuery(CarDO.class);
            Root<CarDO> rootEntry = criteriaQuery.from(CarDO.class);


            List<Predicate> predicateList = new ArrayList<>();

            if(null != carDO.getIsConvertible()){
                predicateList.add(criteriaBuilder.equal(rootEntry.get("isConvertible"), carDO.getIsConvertible()));
            }

            if(null != carDO.getEngineType()){
                predicateList.add(criteriaBuilder.equal(rootEntry.get("engineType"), carDO.getEngineType()));
            }

            if(null != carDO.getLicensePlate()){
                predicateList.add(criteriaBuilder.equal(rootEntry.get("licensePlate"), carDO.getLicensePlate()));
            }

            if(null != carDO.getManufacturer()){
                predicateList.add(criteriaBuilder.equal(rootEntry.get("manufacturer"), carDO.getManufacturer()));
            }

            if(null != carDO.getRating()){
                predicateList.add(criteriaBuilder.equal(rootEntry.get("rating"), carDO.getRating()));
            }

            if(null != carDO.getSeatCount()){
                predicateList.add(criteriaBuilder.equal(rootEntry.get("seatCount"), carDO.getSeatCount()));
            }

            if(!(predicateList.isEmpty())){
                criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[]{})));
            }

            return entityManager.createQuery(criteriaQuery).getResultList();
        }else{
            return null;
        }



    }
}
