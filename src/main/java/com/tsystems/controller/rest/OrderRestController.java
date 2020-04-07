package com.tsystems.controller.rest;

import com.tsystems.Util.ConverterUtil;
import com.tsystems.entity.*;
import com.tsystems.entity.Converter.Converter;
import com.tsystems.entity.enums.OrderStatus;
import com.tsystems.entity.enums.WagonStatus;
import com.tsystems.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {
    private CargoService cargoService;
    private WagonService wagonService;
    private DriverService driverService;
    private OrderService orderService;
    private DriverShiftService driverShiftService;

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Autowired
    public void setWagonService(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setDriverShiftService(DriverShiftService driverShiftService) {
        this.driverShiftService = driverShiftService;
    }

    @GetMapping("employee/order/cargolist")
    public String getAllCargoes() {
        List<Cargo> cargoList = cargoService.getAllCargoes();
        return ConverterUtil.parseJson(Converter.getCargoDtos(cargoList));
    }

    @GetMapping("employee/wagon/wagonlist")
    public String getAllWagons() {
        List<Wagon> wagonList = wagonService.getAllWagons();
        List<Order> orderList = orderService.getAllOrders();
        List<Wagon> resultWagonList = new ArrayList<>();
        Boolean isWagonFree = true;
        for(Wagon wagon : wagonList) {
            isWagonFree = true;
            for(Order order : orderList) {
                if (order.getWagon().getId() == wagon.getId() && order.getStatus() != OrderStatus.DONE) {
                    isWagonFree = false;
                }
            }
            if(isWagonFree) {
                resultWagonList.add(wagon);
            }
        }
        return ConverterUtil.parseJson(Converter.getWagonDtos(resultWagonList));
    }

    @GetMapping("employee/driver/driverlist")
    public String getAllDrivers() {
        List<Driver> driverList = driverService.getAllDriversWithoutWagon();
        List<DriverShift> driverShiftList = driverShiftService.getAllDriversShifts();
        return ConverterUtil.parseJson(Converter.getDriverDtos(driverList, driverShiftList));
    }
}
