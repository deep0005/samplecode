package com.mindgeek.samplecode.controller.mapper;

import com.mindgeek.samplecode.datatransferobject.CarDTO;
import com.mindgeek.samplecode.domainobject.CarDO;
import org.springframework.beans.BeanUtils;

public class CarMapper {

    public static CarDTO createCarDTO(CarDO carDO){
        CarDTO carDTO = new CarDTO();
        BeanUtils.copyProperties(carDO, carDTO);
        return carDTO;
    }

    /*public static List<CarDTO> createCarDTOList(List<CarDO> carDTOList){
        return carDTOList.stream().map((carDO) -> {
            CarDTO carDTO = new CarDTO();
            BeanUtils.copyProperties(carDO, carDTO);
            return carDTO;
        }).collect(Collectors.toList());
    }*/
}
