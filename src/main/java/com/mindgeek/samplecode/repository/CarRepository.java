package com.mindgeek.samplecode.repository;

import com.mindgeek.samplecode.domainobject.CarDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<CarDO, Long>, CustomCarRepository {


    List<CarDO> findByLicensePlate(String licensePlate);

}
