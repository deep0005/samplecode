package com.mindgeek.samplecode.service.car;

import com.mindgeek.samplecode.domainobject.CarDO;
import com.mindgeek.samplecode.exception.CarAlreadyInUseException;
import com.mindgeek.samplecode.exception.EntityNotFoundException;

import java.util.List;


public interface CarService {
    CarDO find(Long id) throws EntityNotFoundException;
    CarDO find(String licensePlate) throws EntityNotFoundException;
    List<CarDO> find(CarDO carDO);
    CarDO create(CarDO carDO);
    void delete(String licensePlate) throws EntityNotFoundException, CarAlreadyInUseException;
}
