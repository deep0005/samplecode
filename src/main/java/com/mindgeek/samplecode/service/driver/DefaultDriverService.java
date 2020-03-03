package com.mindgeek.samplecode.service.driver;

import com.mindgeek.samplecode.repository.DriverRepository;
import com.mindgeek.samplecode.domainobject.CarDO;
import com.mindgeek.samplecode.domainobject.DriverDO;
import com.mindgeek.samplecode.domainvalue.GeoCoordinate;
import com.mindgeek.samplecode.domainvalue.OnlineStatus;
import com.mindgeek.samplecode.exception.*;
import com.mindgeek.samplecode.service.car.CarService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Driver;
import java.util.List;
import java.util.Optional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    @Autowired
    private CarService carService;


    public DefaultDriverService(final DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException {
        return findDriverChecked(driverId);
    }

    /**
     * Find a driver by username
     *
     * @param username
     * @return
     * @throws EntityNotFoundException - if no driver found under mentioned username
     */
    @Override
    public DriverDO find(String username) throws EntityNotFoundException {
        return driverRepository.findByUsername(username)
                .stream()
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Driver not found with username " + username));
    }

    /**
     * Find a driver by car id
     * @param carDO
     * @return
     */
    @Override
    public DriverDO findByCar(CarDO carDO) {
        return driverRepository.findByCarId(carDO)
                .stream()
                .findFirst().orElse(null);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException {
        DriverDO driver;
        try {
            driver = driverRepository.save(driverDO);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException("Constraints were violated while creating driver with username " + driverDO.getUsername());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus) {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }

    /**
     * Select a car for a driver
     * @param username
     * @param licensePlate
     * @throws EntityNotFoundException - if no car or driver found
     * @throws CarAlreadyInUseException - if car is already in use by another driver
     * @throws DriverOfflineException - if driver is offline
     */
    @Transactional
    public void selectCar(String username, String licensePlate) throws EntityNotFoundException, CarAlreadyInUseException, DriverOfflineException {

        DriverDO targetDriver = driverRepository.findByUsername(username).stream().findFirst().orElse(null);

        if (null != targetDriver) {
            if(LOG.isDebugEnabled()){
                LOG.debug("Driver with username " + username + " found in repository");
            }
            if (targetDriver.getOnlineStatus().equals(OnlineStatus.ONLINE)) {
                CarDO carDO = carService.find(licensePlate);
                if(LOG.isDebugEnabled()){
                    LOG.debug("Car with licensePlate " + licensePlate + " found in repository");
                }
                DriverDO driverWithCar = driverRepository.findByCarId(carDO).stream().findFirst().orElse(null);
                if (null == driverWithCar) {
                    targetDriver.setCarId(carDO);
                    if(LOG.isDebugEnabled()){
                        LOG.debug("Car with licensePlate " + licensePlate + " is assigned to driver with username " + username);
                    }
                } else {
                    throw new CarAlreadyInUseException("Car is already selected by any other driver");
                }
            } else {
                LOG.error("Cannot attempt car selection process while request driver is offline " + username);
                throw new DriverOfflineException("Cannot select a car while offline.");
            }
        } else {
            throw new EntityNotFoundException("Driver not found with username " + username);
        }

    }

    /**
     * Deselect a car from a driver
     * @param username
     * @param licensePlate
     * @throws EntityNotFoundException - if no car or driver or relation between car-driver found
     * @throws CarAlreadyInUseException - if request car is being used by another driver
     */
    @Transactional
    public void deselectCar(String username, String licensePlate) throws EntityNotFoundException, CarAlreadyInUseException {

        CarDO carDO = carService.find(licensePlate);
        DriverDO driverWithCar = driverRepository.findByCarId(carDO).stream().findFirst().orElse(null);

        if (null != driverWithCar) {
            if (driverWithCar.getUsername().equals(username)) {
                driverWithCar.setCarId(null);
            } else {
                LOG.error("Cannot deselect as car is assigned to some other driver");
                throw new CarAlreadyInUseException("Specified car is not registered under driver with username " + username);
            }
        } else {
            throw new EntityNotFoundException("Cannot deselect a free car.");
        }

    }

    /**
     * Performs a search drivers based on mentioned params
     * @param username - optional
     * @param onlineStatus - optional
     * @param carDO - optional
     * @return
     * @throws ContentNotFoundException - if no driver found for mentioned params
     */
    public List<DriverDO> searchDrivers(String username, OnlineStatus onlineStatus, CarDO carDO) throws ContentNotFoundException{


        List<DriverDO> driverDOList = driverRepository.getDriversByCustomAttributes(username, onlineStatus, carService.find(carDO));
        if(!(driverDOList.isEmpty())){
            return driverDOList;
        }else{
            if(LOG.isDebugEnabled()){
                LOG.debug("No content found while searching with username " + username + " with Online Status " + onlineStatus);
            }

            throw new ContentNotFoundException("No drivers were found matching request params");
        }
    }


    /**
     * Set driver status to ONLINE
     * @param username
     * @throws ContentNotFoundException
     */
    @Override
    @Transactional
    public void loginDriver(String username) throws ContentNotFoundException{

        DriverDO driver = driverRepository.findByUsername(username).stream().findFirst().orElseThrow(() -> new ContentNotFoundException("Driver not found"));
        driver.setOnlineStatus(OnlineStatus.ONLINE);
        driverRepository.save(driver);
    }



}
