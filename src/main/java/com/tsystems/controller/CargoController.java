package com.tsystems.controller;

import com.tsystems.entity.Cargo;
import com.tsystems.entity.City;
import com.tsystems.entity.enums.CargoStatus;
import com.tsystems.service.api.CargoService;
import com.tsystems.service.api.CityService;
import com.tsystems.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/employee/cargo")
public class CargoController {
    private CargoService cargoService;
    private CityService cityService;
    private OrderService orderService;

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/add")
    public ModelAndView addCargoPage() {
        ModelAndView mv = new ModelAndView();
        Cargo cargo = new Cargo();
        List<City> cities = cityService.getAllCities();
        mv.addObject("cargo", cargo);
        mv.addObject("cities", cities);
        mv.setViewName("views/emp/empAddCargo");
        return mv;
    }

    @PostMapping("/add")
    public String addCargo(@ModelAttribute("cargo") Cargo cargo, @RequestParam("city_from") Integer city_from, @RequestParam("city_to") Integer city_to) {
        City cityFrom = cityService.findCityById(city_from);
        City cityTo = cityService.findCityById(city_to);
        cargo.setCity_from(cityFrom);
        cargo.setCity_to(cityTo);
        cargo.setStatus(CargoStatus.PREPARED);
        cargoService.addCargo(cargo);
        return "redirect:/employee/cargoes";
    }
}
