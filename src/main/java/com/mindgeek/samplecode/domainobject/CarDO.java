package com.mindgeek.samplecode.domainobject;


import com.mindgeek.samplecode.domainvalue.EngineType;
import com.mindgeek.samplecode.domainvalue.Rating;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(
        name = "car",
        uniqueConstraints = @UniqueConstraint(name = "uc_licensePlate", columnNames = {"licensePlate"})
)
public class CarDO{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @NotBlank(message = "Licence plate number cannot be blank")
    private String licensePlate;

    @Column(nullable = false)
    @NotNull(message = "Seat count cannot be null")
    private Integer seatCount;

    @Column(nullable = false)
    private Boolean isConvertible = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rating rating = Rating.AVERAGE;

    @Column(nullable = false)
    @NotNull(message = "Engine type cannot be null")
    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @Column(nullable = false)
    @NotBlank(message = "Manufacturer cannot be blank")
    private String manufacturer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
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
