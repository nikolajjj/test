package com.tsystems.service.implementation;

import com.tsystems.dao.api.*;
import com.tsystems.entity.Cargo;
import com.tsystems.entity.Driver;
import com.tsystems.entity.DriverShift;
import com.tsystems.entity.Order;
import com.tsystems.entity.enums.CargoStatus;
import com.tsystems.entity.enums.DriverStatus;
import com.tsystems.entity.enums.OrderStatus;
import com.tsystems.service.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {
    private CargoDAO cargoDAO;
    private OrderDAO orderDAO;
    private DriverDAO driverDAO;
    private WagonDAO wagonDAO;
    private DriverShiftDAO driverShiftDAO;

    @Autowired
    public void setCargoDAO(CargoDAO cargoDAO) {
        this.cargoDAO = cargoDAO;
    }

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Autowired
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Autowired
    public void setWagonDAO(WagonDAO wagonDAO) {
        this.wagonDAO = wagonDAO;
    }

    @Autowired
    public void setDriverShiftDAO(DriverShiftDAO driverShiftDAO) {
        this.driverShiftDAO = driverShiftDAO;
    }

    @Override
    @Transactional
    public void addCargo(Cargo cargo) {
        cargoDAO.add(cargo);
    }

    @Override
    @Transactional
    public Cargo findCargoById(Integer id) {
        return cargoDAO.findById(id);
    }

    @Override
    @Transactional
    public List<Cargo> findCargoByOrderId(Integer id) {
        List<Cargo> cargoList = cargoDAO.getAll();
        List<Cargo> resultList = new ArrayList<>();
        for(Cargo cargo : cargoList) {
            if (cargo.getOrder() != null) {
                if (cargo.getOrder().getId() == id) {
                    resultList.add(cargo);
                }
            }
        }
        return resultList;
    }

    @Override
    @Transactional
    public void updateCargo(Cargo cargo) {
        cargoDAO.update(cargo);
        List<Cargo> cargoList = cargoDAO.getCargoesByOrderId(cargo.getOrder().getId());
        List<Driver> driverList = driverDAO.getDriversByWagonId(cargo.getOrder().getWagon().getId());
        Boolean isCargoesAreAllDelivered = true;
        for(Cargo temp : cargoList) {
            if(temp.getStatus() != CargoStatus.DELIVERED) {
                isCargoesAreAllDelivered = false;
            }
        }
        if(isCargoesAreAllDelivered) {
            Order order = orderDAO.findById(cargo.getOrder().getId());
            order.setStatus(OrderStatus.DONE);
            List<DriverShift> driverShiftList = new ArrayList<>();
            for(Driver driver : driverList) {
                driver.setStatus(DriverStatus.REST);
                driver.setCurrent_wagon(null);
                driverShiftList.add(driverShiftDAO.getLastDriverShiftByDriverId(driver.getId()));
            }
            for(DriverShift driverShift : driverShiftList) {
                driverShift.setEnd(new Date());
            }
        }
    }

    @Override
    @Transactional
    public void deleteCargo(Cargo cargo) {
        cargoDAO.delete(cargo);
    }

    @Override
    @Transactional
    public List<Cargo> getAllCargoes() {
        return cargoDAO.getAll();
    }
}
