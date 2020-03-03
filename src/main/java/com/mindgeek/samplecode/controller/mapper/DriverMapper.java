package com.mindgeek.samplecode.controller.mapper;

import com.mindgeek.samplecode.datatransferobject.DriverDTO;
import com.mindgeek.samplecode.domainobject.DriverDO;
import com.mindgeek.samplecode.domainvalue.GeoCoordinate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DriverMapper
{
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword());
    }


    public static DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(driverDO.getId())
            .setPassword(driverDO.getPassword())
            .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (null != coordinate)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        return driverDTOBuilder.createDriverDTO();
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream()
            .map(DriverMapper::makeDriverDTO)
            .collect(Collectors.toList());
    }
}
