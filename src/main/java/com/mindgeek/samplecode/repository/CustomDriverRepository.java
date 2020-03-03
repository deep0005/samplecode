package com.mindgeek.samplecode.repository;

import com.mindgeek.samplecode.domainobject.CarDO;
import com.mindgeek.samplecode.domainobject.DriverDO;
import com.mindgeek.samplecode.domainvalue.OnlineStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomDriverRepository {

    List<DriverDO> getDriversByCustomAttributes(String userName, OnlineStatus onlineStatus, List<CarDO> carDOList);
}
