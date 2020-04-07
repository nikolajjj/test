package com.tsystems.service.implementation;

import com.tsystems.dao.api.DriverDAO;
import com.tsystems.dao.api.OrderDAO;
import com.tsystems.dao.api.WagonDAO;
import com.tsystems.entity.Driver;
import com.tsystems.entity.Order;
import com.tsystems.entity.Wagon;
import com.tsystems.entity.enums.DriverStatus;
import com.tsystems.entity.enums.OrderStatus;
import com.tsystems.service.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    private DriverDAO driverDAO;
    private OrderDAO orderDAO;
    private WagonDAO wagonDAO;

    public DriverServiceImpl(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Autowired
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Autowired
    public void setWagonDAO(WagonDAO wagonDAO) {
        this.wagonDAO = wagonDAO;
    }

    @Override
    @Transactional
    public void addDriver(Driver driver) {
        driverDAO.add(driver);
    }

    @Override
    @Transactional
    public Driver findDriverById(Integer Id) {
        return driverDAO.findById(Id);
    }

    @Override
    @Transactional
    public Driver findDriverByUserId(Integer id) {
        List<Driver> driverList = driverDAO.getAll();
        for(Driver driver : driverList) {
            if (driver.getUser_id() != null) {
                if (driver.getUser_id().getId() == id) {
                    return driver;
                }
            }
        }
        return null;
    }


    @Override
    @Transactional
    public List<Driver> findCoDriverByWagonId(Driver driver, Integer id) {
        List<Driver> driverList = driverDAO.getAll();
        List<Driver> coDrivers = new ArrayList<>();
        for(Driver temp : driverList) {
            if (temp.getCurrent_wagon() != null) {
                if ((temp.getCurrent_wagon().getId() == id) && (driver.getId() != temp.getId())) {
                    coDrivers.add(temp);
                }
            }
        }
        return coDrivers;
    }

    @Override
    @Transactional
    public void updateDriver(Driver driver) {
        if (driver.getCurrent_wagon() != null) {
            Order order = orderDAO.getOrderByWagonId(wagonDAO.findById(driver.getCurrent_wagon().getId()).getId());
            List<Driver> driverList = driverDAO.getCoDrivers(driver.getId(), driver.getCurrent_wagon().getId());
            if (order.getStatus() == OrderStatus.DONE) {
                return;
            }
        }
        driverDAO.update(driver);
    }

    @Override
    @Transactional
    public void deleteDriver(Driver driver) {
        driverDAO.delete(driver);
    }

    @Override
    @Transactional
    public List<Driver> getAllDrivers() {
        return driverDAO.getAll();
    }

    @Override
    @Transactional
    public List<Driver> getAllDriversWithoutWagon() {
        List<Driver> driverList = driverDAO.getAll();
        List<Driver> driversWithoutWagon = new ArrayList<>();
        for(Driver driver : driverList) {
            if (driver.getCurrent_wagon() == null) {
                driversWithoutWagon.add(driver);
            }
        }
        return driversWithoutWagon;
    }
}
