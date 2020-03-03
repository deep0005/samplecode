package com.mindgeek.samplecode.controller;

import com.mindgeek.samplecode.controller.mapper.CarMapper;
import com.mindgeek.samplecode.datatransferobject.CarDTO;
import com.mindgeek.samplecode.domainobject.CarDO;
import com.mindgeek.samplecode.exception.CarAlreadyInUseException;
import com.mindgeek.samplecode.exception.ConstraintsViolationException;
import com.mindgeek.samplecode.exception.DataValidationException;
import com.mindgeek.samplecode.exception.EntityNotFoundException;
import com.mindgeek.samplecode.service.car.CarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/cars")
public class CarController {

    @Autowired
    private CarService carService;

    /**
     * Fetch car details by License Plate Number
     * @param licensePlate - queryParam
     * @param bindingResult used in case of binding error
     * @return car details
     * @throws EntityNotFoundException if no car found for mentioned license plate number
     * @throws DataValidationException if license plate number is not valid
     */
    @RequestMapping(
            method = RequestMethod.GET,
            headers = "X-Requested-With=XMLHttpRequest"
    )
    @CrossOrigin(
            origins = { "*" },
            methods = { RequestMethod.GET },
            allowCredentials = "true",
            allowedHeaders = CorsConfiguration.ALL,
            exposedHeaders = {},
            maxAge = 1800)
    public CarDTO getCarByLicensePlate(@RequestParam("licensePlate") String licensePlate) throws EntityNotFoundException{

        return CarMapper.createCarDTO(carService.find(licensePlate));
    }

    /**
     * Fetch car details by car id
     * @param carId - pathVariable
     * @param bindingResult used in case of binding error
     * @return car details
     * @throws EntityNotFoundException if no car found for mentioned car id
     * @throws DataValidationException if car id is null
     */
    @RequestMapping(
            value = "/{carId}",
            method = RequestMethod.GET,
            headers = "X-Requested-With=XMLHttpRequest"
    )
    @CrossOrigin(
            origins = { "*" },
            methods = { RequestMethod.GET },
            allowCredentials = "true",
            allowedHeaders = CorsConfiguration.ALL,
            exposedHeaders = {},
            maxAge = 1800)
    public CarDTO getCar(@PathVariable("carId") Long carId) throws EntityNotFoundException {
        return CarMapper.createCarDTO(carService.find(carId));
    }

    /**
     * Delete car by license plate
     * @param licensePlate - queryParam
     * @param bindingResult used in case of binding error
     * @throws EntityNotFoundException if no car found for mentioned license plate number
     * @throws CarAlreadyInUseException if car is selected by any driver
     * @throws DataValidationException - if license plate number is invalid
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            headers = "X-Requested-With=XMLHttpRequest"
    )
    @CrossOrigin(
            origins = { "*" },
            methods = { RequestMethod.DELETE },
            allowCredentials = "true",
            allowedHeaders = CorsConfiguration.ALL,
            exposedHeaders = {},
            maxAge = 1800)
    public void deleteCarByLicensePlate(@PathParam("licensePlate") String licensePlate) throws EntityNotFoundException, CarAlreadyInUseException {
        carService.delete(licensePlate);
    }

    /**
     * Create new car
     * @param carDTO - requestBody
     * @param bindingResult used in case of binding error
     * @throws ConstraintsViolationException If car already exists for given input
     * @throws DataValidationException If given input is invalid
     */
    @RequestMapping(
            method = RequestMethod.POST,
            headers = "X-Requested-With=XMLHttpRequest"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(
            origins = { "*" },
            methods = { RequestMethod.POST },
            allowCredentials = "true",
            allowedHeaders = CorsConfiguration.ALL,
            exposedHeaders = {},
            maxAge = 1800)
    public void createCar(@Valid @RequestBody CarDTO carDTO, BindingResult bindingResult) throws ConstraintsViolationException, DataValidationException {
        validateBindingResult(bindingResult);

        try {
            CarDO carDO = new CarDO();
            BeanUtils.copyProperties(carDTO, carDO, getNullPropertyNames(carDTO));
            carService.create(carDO);
        }catch(DataIntegrityViolationException e){
            throw new ConstraintsViolationException("Constraints were violated while creating car with license plate " + carDTO.getLicensePlate());
        }
    }

    /**
     * Collect validation error while binding
     * @param bindingResult used in case of binding error
     * @throws DataValidationException in case any error found while validation
     */
    private void validateBindingResult(BindingResult bindingResult) throws DataValidationException{
        if(bindingResult.hasErrors()){
            throw new DataValidationException(bindingResult.getAllErrors()
                                                .stream()
                                                .map((errorObject) -> {
                                                    return errorObject.getDefaultMessage();
                                                }).collect(Collectors.toList()).toString());

        }
    }


    /**
     * Avoid copying null values while calling BeanUtils.copyProperties
     * @param source - Object to be examined
     * @return - Array of property names whose value is null
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
