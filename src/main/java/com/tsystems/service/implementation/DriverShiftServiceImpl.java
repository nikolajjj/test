package com.tsystems.service.implementation;

import com.tsystems.dao.api.DriverShiftDAO;
import com.tsystems.entity.Driver;
import com.tsystems.entity.DriverShift;
import com.tsystems.service.api.DriverShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverShiftServiceImpl implements DriverShiftService {
    private DriverShiftDAO driverShiftDAO;

    @Autowired
    public void setDriverShiftDAO(DriverShiftDAO driverShiftDAO) {
        this.driverShiftDAO = driverShiftDAO;
    }

    @Override
    @Transactional
    public void addDriverShift(DriverShift driverShift) {
        driverShiftDAO.add(driverShift);
    }

    @Override
    @Transactional
    public List<DriverShift> findDriverShiftsByDriver(Driver driver) {
        List<DriverShift> driverShiftList = driverShiftDAO.getAll();
        List<DriverShift> assignedDriverShifts = new ArrayList<>();
        for(DriverShift driverShift : driverShiftList) {
            if (driverShift.getDriver().getId() == driver.getId()) {
                assignedDriverShifts.add(driverShift);
            }
        }
        return assignedDriverShifts;
    }

    @Override
    @Transactional
    public DriverShift getLastDriverShift(Integer driverId) {
        return driverShiftDAO.getLastDriverShiftByDriverId(driverId);
    }

    @Override
    @Transactional
    public void updateDriverShift(DriverShift driverShift) {
        driverShiftDAO.update(driverShift);
    }

    @Override
    @Transactional
    public void deleteDriverShift(DriverShift driverShift) {
        driverShiftDAO.delete(driverShift);
    }

    @Override
    @Transactional
    public List<DriverShift> getAllDriversShifts() {
        return driverShiftDAO.getAll();
    }
}
