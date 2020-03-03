package com.mindgeek.samplecode.repository;

import com.mindgeek.samplecode.domainobject.CarDO;
import com.mindgeek.samplecode.domainobject.DriverDO;
import com.mindgeek.samplecode.domainvalue.OnlineStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>, CustomDriverRepository{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);
    List<DriverDO> findByUsername(String username);
    List<DriverDO> findByCarId(CarDO carId);
}
