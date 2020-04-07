package com.tsystems.controller;

import com.tsystems.Util.HashPasswordUtil;
import com.tsystems.entity.*;
import com.tsystems.entity.enums.DriverStatus;
import com.tsystems.entity.enums.Role;
import com.tsystems.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private DriverService driverService;
    private CargoService cargoService;
    private CityService cityService;
    private WagonService wagonService;
    private UserService userService;

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setWagonService(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/drivers")
    public ModelAndView empDriversIndexPage() {
        ModelAndView mv = new ModelAndView();
        List<Driver> driverList = driverService.getAllDrivers();
        mv.setViewName("views/emp/empDriver");
        mv.addObject("driverList", driverList);
        return mv;
    }

    @GetMapping("/cargoes")
    public ModelAndView empCargoesIndexPage() {
        ModelAndView mv = new ModelAndView();
        List<Cargo> cargoList = cargoService.getAllCargoes();
        mv.setViewName("views/emp/empCargo");
        mv.addObject("cargoList", cargoList);
        return mv;
    }

    @GetMapping("/driver/add")
    public ModelAndView addNewDriverPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/views/emp/empAddDriver");
        Driver driver = new Driver();
        List<City> cities = cityService.getAllCities();
        List<Wagon> wagons = wagonService.getAllWagons();
        mv.addObject("cities", cities);
        mv.addObject("driver", driver);
        mv.addObject("wagons", wagons);
        return mv;
    }

    @PostMapping("/driver/add")
    @Transactional
    public String addNewDriver(@ModelAttribute("driver") Driver driver, @RequestParam("city_id") Integer cityId, @RequestParam(value = "wagon_id", defaultValue = "0") Integer wagonId) {
        User user = new User();
        user.setRole(Role.DRIVER);
        user.setUsername(driver.getFirst_name());
        user.setPassword(HashPasswordUtil.getHash(driver.getFirst_name().toString()));
        if(wagonId != 0) {
            driver.setCurrent_wagon(wagonService.findWagonById(wagonId));
        }
        driver.setStatus(DriverStatus.REST);
        driver.setUser_id(user);
        driver.setCurrent_city(cityService.findCityById(cityId));
        driverService.addDriver(driver);
        return "redirect:/employee/drivers";
    }

    @GetMapping("driver/update={driverId}")
    public ModelAndView updateDriverPage(@PathVariable("driverId") Integer driverId) {
        ModelAndView mv = new ModelAndView();
        Driver driver = driverService.findDriverById(driverId);
        List<City> cities = cityService.getAllCities();
        List<Wagon> wagons = wagonService.getAllWagons();
        City current_city = driver.getCurrent_city();
        Wagon current_wagon = driver.getCurrent_wagon();
        mv.setViewName("views/emp/empEditDriver");
        mv.addObject("driver", driver);
        mv.addObject("cities", cities);
        mv.addObject("wagons", wagons);
        return mv;
    }

    @PostMapping("driver/update={driverId}")
    public String updateDriver(@ModelAttribute("driver") Driver driver, @RequestParam(value = "city_id", defaultValue = "0") Integer cityId,
                               @RequestParam(value = "wagon_id", defaultValue = "0") Integer wagonId) {
        if (cityId != 0) {
            driver.setCurrent_city(cityService.findCityById(cityId));
        }
        if (wagonId != 0) {
            driver.setCurrent_wagon(wagonService.findWagonById(wagonId));
        }
        driverService.updateDriver(driver);
        return "redirect:/employee/drivers";
    }

    @PostMapping("driver/delete={driverId}")
    @Transactional
    public String deleteDriver(@PathVariable("driverId") Integer id) {
        driverService.deleteDriver(driverService.findDriverById(id));
        return "redirect:/employee/drivers";
    }
}
