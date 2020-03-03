package com.mindgeek.samplecode.repository;

import com.mindgeek.samplecode.domainobject.CarDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCarRepository {

    List<CarDO> getCarByCustomAttributes(CarDO carDO);
}
