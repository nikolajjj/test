package com.tsystems.service.api;

import com.tsystems.entity.Driver;

import java.util.List;

/**
 *
 */
public interface DriverService {
    /**
     *
     */
    void addDriver(Driver driver);

    /**
     *
     * @param id - Driver id
     * @return
     */
    Driver findDriverById(Integer id);

    /**
     *
     * @param id - User id
     * @return
     */
    Driver findDriverByUserId(Integer id);

    /**
     *
     * @param id - Wagon Id
     * @return
     */
    List<Driver> findCoDriverByWagonId(Driver driver, Integer id);

    /**
     *
     * @param driver
     */
    void updateDriver(Driver driver);

    /**
     *
     * @param driver
     */
    void deleteDriver(Driver driver);

    /**
     *
     * @return
     */
    List<Driver> getAllDrivers();

    /**
     *
     * @return
     */
    List<Driver> getAllDriversWithoutWagon();
}
