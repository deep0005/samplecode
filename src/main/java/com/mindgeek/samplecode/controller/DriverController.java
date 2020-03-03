package com.mindgeek.samplecode.controller;

import com.mindgeek.samplecode.controller.mapper.DriverMapper;
import com.mindgeek.samplecode.datatransferobject.DriverDTO;
import com.mindgeek.samplecode.datatransferobject.DriverSearchRequestDTO;
import com.mindgeek.samplecode.datatransferobject.SelectCarRequestDTO;
import com.mindgeek.samplecode.domainobject.CarDO;
import com.mindgeek.samplecode.domainobject.DriverDO;
import com.mindgeek.samplecode.domainvalue.OnlineStatus;
import com.mindgeek.samplecode.exception.*;
import com.mindgeek.samplecode.service.driver.DriverService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public DriverController(final DriverService driverService) {
        this.driverService = driverService;
    }

    /**
     * Fetch driver details by driver id
     * @param driverId - pathVariable
     * @param bindingResult
     * @return driver details
     * @throws EntityNotFoundException if no driver found for given driver id
     * @throws DataValidationException if driver id is invalid
     */
    @RequestMapping(
            value = "/{driverId}",
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
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException {

        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    /**
     * Create driver
     * @param driverDTO - requestBody
     * @param bindingResult
     * @return
     * @throws ConstraintsViolationException if driver already exists
     * @throws DataValidationException if parameters are invalid
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
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO, BindingResult bindingResult) throws ConstraintsViolationException, DataValidationException {
        validateBindingResult(bindingResult);

        DriverDTO encodedDriver = DriverDTO.newBuilder()
                                        .setUsername(driverDTO.getUsername())
                                        .setPassword(bCryptPasswordEncoder.encode(driverDTO.getPassword()))
                                        .setCoordinate(driverDTO.getCoordinate())
                                        .createDriverDTO();
        DriverDO driverDO = DriverMapper.makeDriverDO(encodedDriver);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }

    /**
     * Delete a driverz
     * @param driverId - pathVariable
     * @param bindingResult
     * @throws EntityNotFoundException if no driver found under mentioned driver id
     * @throws DataValidationException if driver id is invalid
     */
    @RequestMapping(
            value = "/{driverId}",
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
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException {
        driverService.delete(driverId);
    }


    /**
     * Update driver geo coordinates
     * @param driverId
     * @param latitude
     * @param bindingResult
     * @throws ConstraintsViolationException if database constraints are being violated
     * @throws EntityNotFoundException if no driver found under driver id
     * @throws DataValidationException if parameters are invalid
     */
    @RequestMapping(
            value = "/{driverId}",
            method = RequestMethod.PUT,
            headers = "X-Requested-With=XMLHttpRequest"
    )
    @CrossOrigin(
            origins = { "*" },
            methods = { RequestMethod.PUT },
            allowCredentials = "true",
            allowedHeaders = CorsConfiguration.ALL,
            exposedHeaders = {},
            maxAge = 1800)
    public void updateLocation(
            @Valid @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
            throws EntityNotFoundException {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    /**
     * Find drivers based on their online status
     * @param onlineStatus - requestParam
     * @return
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
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus) throws DataValidationException{
        if(null == onlineStatus){
            throw new DataValidationException("Invalid online status");
        }
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }

    /**
     * Select a car for a driver
     * @param selectCarRequestDTO - requestBody
     * @param bindingResult
     * @throws EntityNotFoundException - if no car or driver found for mentioned parameters
     * @throws CarAlreadyInUseException - if car is being used by any other driver
     * @throws DriverOfflineException - if driver is offline
     * @throws DataValidationException - if parameters are invalid
     */
    @RequestMapping(
            value = "/select",
            method = RequestMethod.POST,
            headers = "X-Requested-With=XMLHttpRequest"
    )
    @CrossOrigin(
            origins = { "*" },
            methods = { RequestMethod.POST },
            allowCredentials = "true",
            allowedHeaders = CorsConfiguration.ALL,
            exposedHeaders = {},
            maxAge = 1800)
    public void selectCar(@Valid @RequestBody SelectCarRequestDTO selectCarRequestDTO, BindingResult bindingResult) throws EntityNotFoundException, CarAlreadyInUseException, DriverOfflineException, DataValidationException {
        validateBindingResult(bindingResult);

        driverService.selectCar(selectCarRequestDTO.getUsername(), selectCarRequestDTO.getLicensePlate());
    }

    /**
     * Deselect a car from a driver
     * @param selectCarRequestDTO
     * @param bindingResult
     * @throws EntityNotFoundException - if no car or driver found for mentioned parameters or no relation found between mentioned driver and car
     * @throws CarAlreadyInUseException - if mentioned car is selected by any other driver
     * @throws DataValidationException - if parameters are invalid
     */
    @RequestMapping(
            value = "/deselect",
            method = RequestMethod.POST,
            headers = "X-Requested-With=XMLHttpRequest"
    )
    @CrossOrigin(
            origins = { "*" },
            methods = { RequestMethod.POST },
            allowCredentials = "true",
            allowedHeaders = CorsConfiguration.ALL,
            exposedHeaders = {},
            maxAge = 1800)
    public void deselectCar(@Valid @RequestBody SelectCarRequestDTO selectCarRequestDTO, BindingResult bindingResult) throws EntityNotFoundException, CarAlreadyInUseException, DataValidationException {
        validateBindingResult(bindingResult);

        driverService.deselectCar(selectCarRequestDTO.getUsername(), selectCarRequestDTO.getLicensePlate());
    }

    /**
     * Search drivers based on driver and car params. Mentioned params will be searched in database
     * @param searchDTO
     * @param bindingResult
     * @return
     * @throws ContentNotFoundException - if no record found for mentioned parameters
     * @throws DataValidationException - if passed params are invalid
     */
    @RequestMapping(
            value = "/search",
            method = RequestMethod.POST,
            headers = "X-Requested-With=XMLHttpRequest"
    )
    @CrossOrigin(
            origins = { "*" },
            methods = { RequestMethod.POST },
            allowCredentials = "true",
            allowedHeaders = CorsConfiguration.ALL,
            exposedHeaders = {},
            maxAge = 1800)
    public List<DriverDTO> searchDrivers(@Valid @RequestBody DriverSearchRequestDTO searchDTO, BindingResult bindingResult) throws ContentNotFoundException, DataValidationException{
        validateBindingResult(bindingResult);

        CarDO carDO = null;
        if (null != searchDTO.getCarDTO()) {
            carDO = new CarDO();

            BeanUtils.copyProperties(searchDTO.getCarDTO(), carDO);
        }

        return DriverMapper.makeDriverDTOList(driverService.searchDrivers(searchDTO.getUsername(), searchDTO.getOnlineStatus(), carDO));
    }

    /**
     * Collect validation error while binding
     * @param bindingResult
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
}
