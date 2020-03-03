package com.mindgeek.samplecode.service.car;

import com.mindgeek.samplecode.repository.CarRepository;
import com.mindgeek.samplecode.domainobject.CarDO;
import com.mindgeek.samplecode.exception.CarAlreadyInUseException;
import com.mindgeek.samplecode.exception.EntityNotFoundException;
import com.mindgeek.samplecode.service.driver.DriverService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultCarService implements CarService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverService driverService;

    @Override
    public CarDO find(Long id) throws EntityNotFoundException {
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find car with id " + id));
    }

    @Override
    public CarDO find(String licensePlate) throws EntityNotFoundException {
        return carRepository.findByLicensePlate(licensePlate)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Could not found car entity with license plate " + licensePlate));
    }


    /**
     * Create car
     * @param carDO
     * @return
     */
    @Override
    @Transactional
    public CarDO create(CarDO carDO) {
        return carRepository.save(carDO);
    }

    /**
     * delete car from database
     * @param licensePlate
     * @throws EntityNotFoundException - if no car found for mentioned license plate number
     * @throws CarAlreadyInUseException - if car is being used by any driver
     */
    @Override
    @Transactional
    public void delete(String licensePlate) throws EntityNotFoundException, CarAlreadyInUseException {
        CarDO carDO = carRepository.findByLicensePlate(licensePlate)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No car found with license plate " + licensePlate));


        if (null == driverService.findByCar(carDO)) {
            carRepository.deleteById(carDO.getId());
        } else {
            throw new CarAlreadyInUseException("Cannot delete the car as it is currently in use");
        }
    }


    /**
     * Fetch car details by attributes
     * @param carDO
     * @return
     */
    @Override
    public List<CarDO> find(CarDO carDO) {
        return carRepository.getCarByCustomAttributes(carDO);
    }
}

