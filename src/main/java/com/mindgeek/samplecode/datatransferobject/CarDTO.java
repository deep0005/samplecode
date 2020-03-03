package com.mindgeek.samplecode.datatransferobject;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mindgeek.samplecode.domainvalue.EngineType;
import com.mindgeek.samplecode.domainvalue.Rating;
import com.mindgeek.samplecode.validator.ValidateLicensePlate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO implements Serializable{

    @JsonIgnore
    private Long id;

    @ValidateLicensePlate
    private String licensePlate;

    @NotNull(message = "Seat count cannot be null")
    private Integer seatCount;

    private Boolean isConvertible;

    private Rating rating;

    private EngineType engineType;

    @NotBlank(message = "Manufacturer cannot be blank")
    private String manufacturer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Boolean getIsConvertible() {
        return isConvertible;
    }

    public void setIsConvertible(Boolean convertible) {
        isConvertible = convertible;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

}
