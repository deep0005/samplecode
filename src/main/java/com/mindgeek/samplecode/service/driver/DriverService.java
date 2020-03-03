package com.mindgeek.samplecode.service.driver;

import com.mindgeek.samplecode.domainobject.CarDO;
import com.mindgeek.samplecode.domainobject.DriverDO;
import com.mindgeek.samplecode.domainvalue.OnlineStatus;
import com.mindgeek.samplecode.exception.*;

import java.util.List;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO find(String username) throws EntityNotFoundException;

    DriverDO findByCar(CarDO carDO);

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

    void selectCar(String username, String licensePlate) throws EntityNotFoundException, CarAlreadyInUseException, DriverOfflineException;

    void deselectCar(String username, String licensePlate) throws EntityNotFoundException, CarAlreadyInUseException;

    List<DriverDO> searchDrivers(String username, OnlineStatus onlineStatus, CarDO carDO) throws ContentNotFoundException;

    void loginDriver(String username) throws ContentNotFoundException;
}
