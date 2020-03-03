package com.mindgeek.samplecode.repository;

import com.mindgeek.samplecode.domainobject.CarDO;
import com.mindgeek.samplecode.domainobject.DriverDO;
import com.mindgeek.samplecode.domainvalue.OnlineStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CustomDriverRepositoryImpl implements CustomDriverRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DriverDO> getDriversByCustomAttributes(String username, OnlineStatus onlineStatus, List<CarDO> carDOList) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DriverDO> criteriaQuery = criteriaBuilder.createQuery(DriverDO.class);
        Root<DriverDO> rootEntry = criteriaQuery.from(DriverDO.class);


        List<Predicate> predicateList = new ArrayList<>();

        if (null != username) {
            predicateList.add(criteriaBuilder.equal(rootEntry.get("username"), username));
        }

        if (null != onlineStatus) {
            predicateList.add(criteriaBuilder.equal(rootEntry.get("onlineStatus"), onlineStatus));
        }

        if (null != carDOList && !(carDOList.isEmpty())) {
            predicateList.add(rootEntry.get("carId").in(carDOList));
        }

        if (!(predicateList.isEmpty())) {
            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[]{})));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
