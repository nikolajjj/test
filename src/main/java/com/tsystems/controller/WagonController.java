package com.tsystems.controller;

import com.tsystems.entity.City;
import com.tsystems.entity.Wagon;
import com.tsystems.service.api.CityService;
import com.tsystems.service.api.WagonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("/employee/wagon")
public class WagonController {
    private WagonService wagonService;
    private CityService cityService;

    @Autowired
    public void setWagonService(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/list")
    public ModelAndView wagonList() {
        ModelAndView mv = new ModelAndView();
        List<Wagon> wagonList = wagonService.getAllWagons();
        mv.setViewName("views/emp/empWagon");
        mv.addObject("wagonList", wagonList);
        return mv;
    }
    @GetMapping("/add")
    public ModelAndView addCargoPage() {
        ModelAndView mv = new ModelAndView();
        Wagon wagon = new Wagon();
        List<City> cities = cityService.getAllCities();
        mv.addObject("wagon", wagon);
        mv.addObject("cities", cities);
        mv.setViewName("views/emp/empAddWagon");
        return mv;
    }

    @PostMapping("/add")
    public String addCargo(@ModelAttribute("wagon") Wagon wagon, @RequestParam("current_city") Integer current_cityId) {
        City current_city = cityService.findCityById(current_cityId);
        wagon.setCurrent_city(current_city);
        wagonService.addWagon(wagon);
        return "redirect:/employee/wagon/list";
    }

    @PostMapping("/delete={id}")
    @Transactional
    public String deleteWagon(@PathVariable("id") Integer id) {
        wagonService.deleteWagon(wagonService.findWagonById(id));
        return "redirect:/employee/wagon/list";
    }

    @GetMapping("/update={wagonId}")
    public ModelAndView updateWagonPage(@PathVariable("wagonId") Integer wagonId) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("views/emp/empEditWagon");
        Wagon wagon = wagonService.findWagonById(wagonId);
        List<City> cityList = cityService.getAllCities();
        mv.addObject("wagon", wagon);
        mv.addObject("selectedCity", wagon.getCurrent_city());
        mv.addObject("cities", cityList);
        return mv;
    }

    @PostMapping("/update={wagonId}")
    /*@Transactional*/
    public String updateWagon(@ModelAttribute("wagon") Wagon wagon, @RequestParam("city_id") Integer cityId) {
        City city = cityService.findCityById(cityId);
        wagon.setCurrent_city(city);
        wagonService.updateWagon(wagon);
        return "redirect:/employee/wagon/list";
    }
}