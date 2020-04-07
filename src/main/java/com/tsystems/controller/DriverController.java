package com.tsystems.controller;

import com.tsystems.entity.*;
import com.tsystems.entity.enums.CargoStatus;
import com.tsystems.entity.enums.DriverStatus;
import com.tsystems.service.api.*;
import com.tsystems.service.implementation.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/driver")
public class DriverController {
    private DriverService driverService;
    private WagonService wagonService;
    private OrderService orderService;
    private CargoService cargoService;
    private DriverShiftService driverShiftService;

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setWagonService(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Autowired
    public void setDriverShiftService(DriverShiftService driverShiftService) {
        this.driverShiftService = driverShiftService;
    }

    @GetMapping("/")
    public String driverPage() {
        return "views/driver/drivers";
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView mv = new ModelAndView();
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver = driverService.findDriverByUserId(user.getId());
        mv.addObject("driver", driver);
        if (driver.getCurrent_wagon() != null) {
            Order order = orderService.findOrderByWagonId(driver.getCurrent_wagon().getId());
            List<Driver> coDrivers = driverService.findCoDriverByWagonId(driver, driver.getCurrent_wagon().getId());
            List<Cargo> cargoList = cargoService.findCargoByOrderId(order.getId());
            mv.addObject("wagon", wagonService.findWagonById(driver.getCurrent_wagon().getId()));
            mv.addObject("drivers", coDrivers);
            mv.addObject("order", order);
            mv.addObject("cargoList", cargoList);
        }
        mv.setViewName("/views/driver/driverProfile");
        return mv;
    }

    @GetMapping("/order")
    public ModelAndView order() {
        ModelAndView mv = new ModelAndView();
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver = driverService.findDriverByUserId(user.getId());
        DriverShift lastDriverShift = driverShiftService.getLastDriverShift(driver.getId());
        if (driver.getCurrent_wagon() != null) {
            Order order = orderService.findOrderByWagonId(driver.getCurrent_wagon().getId()); // get last order with this wagon id
            List<Driver> coDrivers = driverService.findCoDriverByWagonId(driver, driver.getCurrent_wagon().getId());
            List<Cargo> cargoList = cargoService.findCargoByOrderId(order.getId());
            mv.addObject("driverShift", lastDriverShift);
            mv.addObject("driver", driver);
            mv.addObject("cargoList", cargoList);
            mv.addObject("order", order);
        }
        mv.setViewName("/views/driver/driverUpdateOrder");
        return mv;
    }

    @PostMapping("/order/beginDriverShift")
    public String beginDriverShift() {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver = driverService.findDriverByUserId(user.getId());
        DriverShift driverShift = new DriverShift();
        driverShift.setBegin(new Date());
        driverShift.setDriver(driver);
        driverService.updateDriver(driver);
        driverShiftService.addDriverShift(driverShift);
        return "redirect:/driver/order";
    }

    @PostMapping("/order/endDriverShift")
    public String endDriverShift() {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver = driverService.findDriverByUserId(user.getId());
        DriverShift driverShift = driverShiftService.getLastDriverShift(driver.getId());
        driverShift.setEnd(new Date());
        driverService.updateDriver(driver);
        driverShiftService.updateDriverShift(driverShift);
        return "redirect:/driver/order";
    }

    @PostMapping("/order/changeDriverStatus")
    public String changeDriverStatus(@RequestParam("driver_status") String status, @RequestParam("driver_id") Integer driverId) {
        Driver driver = driverService.findDriverById(driverId);
        driver.setStatus(DriverStatus.valueOf(status));
        driverService.updateDriver(driver);
        return "redirect:/driver/order";
    }

    @PostMapping("/order/changeCargoStatus")
    public String changeCargoStatus(@RequestParam("cargo_status") String status, @RequestParam("cargo_id") Integer cargoId) {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver = driverService.findDriverByUserId(user.getId());
        List<Driver> coDrivers = driverService.findCoDriverByWagonId(driver, driver.getCurrent_wagon().getId());
        Cargo cargo = cargoService.findCargoById(cargoId);
        Order order = orderService.findOrderByWagonId(driver.getCurrent_wagon().getId());
        cargo.setStatus(CargoStatus.valueOf(status));
        if(CargoStatus.valueOf(status) != CargoStatus.DELIVERED) {
            System.out.println("NOT DELIVERED");
            driver.setCurrent_city(cargo.getCity_from());
            for(Driver temp : coDrivers) {
                temp.setCurrent_city(cargo.getCity_from());
                driverService.updateDriver(temp);
            }
            Wagon wagon = wagonService.findWagonById(order.getWagon().getId());
            wagon.setCurrent_city(cargo.getCity_from());
            wagonService.updateWagon(wagon);
        } else {
            System.out.println("DELIVERED");
            for(Driver temp : coDrivers) {
                temp.setCurrent_city(cargo.getCity_to());
                driverService.updateDriver(temp);
            }
            driver.setCurrent_city(cargo.getCity_to());
            Wagon wagon = wagonService.findWagonById(order.getWagon().getId());
            wagon.setCurrent_city(cargo.getCity_to());
            wagonService.updateWagon(wagon);
        }
        System.out.println(driver.toString());
        driverService.updateDriver(driver);
        cargoService.updateCargo(cargo);
        return "redirect:/driver/order";
    }
}
