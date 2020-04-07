package com.tsystems.controller;

import com.tsystems.Util.ConverterUtil;
import com.tsystems.dto.CargoDTO;
import com.tsystems.dto.DriverDTO;
import com.tsystems.dto.OrderDTO;
import com.tsystems.entity.Cargo;
import com.tsystems.entity.Driver;
import com.tsystems.entity.Order;
import com.tsystems.entity.Wagon;
import com.tsystems.entity.enums.OrderStatus;
import com.tsystems.service.api.CargoService;
import com.tsystems.service.api.DriverService;
import com.tsystems.service.api.OrderService;
import com.tsystems.service.api.WagonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class OrderController {
    private OrderService orderService;
    private WagonService wagonService;
    private CargoService cargoService;
    private DriverService driverService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setWagonService(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/order")
    public ModelAndView indexOrderPage() {
        ModelAndView mv = new ModelAndView();
        List<Order> orderList = orderService.getAllOrders();
        mv.addObject("orderList", orderList);
        mv.setViewName("/views/emp/empOrder");
        return mv;
    }

    @GetMapping("/order/add")
    public ModelAndView addOrderPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/views/emp/empAddOrder");
        Order order = new Order();
        List<Wagon> wagonList = wagonService.getAllWagons();
        List<Cargo> cargoList = cargoService.getAllCargoes();
        mv.addObject("order", order);
        mv.addObject("wagonList", wagonList);
        mv.addObject("cargoList", cargoList);
        return mv;
    }

    @PostMapping(value = "/order/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView addNewOrder(@RequestBody String json) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/employee/order");
        OrderDTO orderDTO = ConverterUtil.parseCargoDTOLIst(json);
        Order order = new Order();
        Wagon wagon = wagonService.findWagonById(orderDTO.getWagon().getId());
        order.setStatus(OrderStatus.CREATED);
        order.setWagon(wagon);
        orderService.addOrder(order);
        List<CargoDTO> cargoDTOList = orderDTO.getCargo();
        for(CargoDTO cargoDTO : cargoDTOList) {
            Cargo cargo = cargoService.findCargoById(cargoDTO.getId());
            cargo.setOrder(order);
            cargoService.updateCargo(cargo);
        }
        List<DriverDTO> driverDTOList = orderDTO.getDriver();
        for(DriverDTO driverDTO : driverDTOList) {
            Driver driver = driverService.findDriverById(driverDTO.getId());
            driver.setCurrent_wagon(wagon);
            driverService.updateDriver(driver);
        }
        return mv;
    }
}
