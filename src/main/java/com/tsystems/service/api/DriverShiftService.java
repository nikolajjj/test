package com.tsystems.service.api;

import com.tsystems.entity.Driver;
import com.tsystems.entity.DriverShift;

import java.util.List;

public interface DriverShiftService {
    /**
     *
     * @param driverShift
     */
    void addDriverShift(DriverShift driverShift);

    /**
     *
     * @param driver
     * @return
     */
    List<DriverShift> findDriverShiftsByDriver(Driver driver);

    /**
     *
     * @param driverShift
     */
    void updateDriverShift(DriverShift driverShift);

    /**
     *
     * @param driverShift
     */
    void deleteDriverShift(DriverShift driverShift);

    /**
     *
     * @return
     */
    List<DriverShift> getAllDriversShifts();

    /**
     *
     * @param driverId
     * @return
     */
    DriverShift getLastDriverShift(Integer driverId);
}
