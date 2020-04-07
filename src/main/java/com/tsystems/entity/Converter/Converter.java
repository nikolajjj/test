package com.tsystems.entity.Converter;

import com.tsystems.Util.DateUtil;
import com.tsystems.dto.*;
import com.tsystems.entity.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Converter {
    /**
     *
     * @param user
     * @return
     */
    public static UserDTO getUserDto(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }

    /**
     * Returns List of userDTO from list user entity
     *
     * @param userList userList
     * @return list userDTO
     */
    public static List<UserDTO> getUserDtos(List<User> userList) {
        List<UserDTO> userDtosResultList = new ArrayList<>();
        userList.forEach(user -> userDtosResultList.add(Converter.getUserDto(user)));
        return userDtosResultList;
    }

    public static CargoDTO getCargoDto(Cargo cargo) {
        City city_to = cargo.getCity_to(),
                city_from = cargo.getCity_from();
        CityDTO cityDTO_from = new CityDTO(city_from.getId(), city_from.getName(), city_from.getLongitude(), city_from.getLatitude()),
                cityDTO_to = new CityDTO(city_to.getId(), city_to.getName(), city_to.getLongitude(), city_to.getLatitude());
        return new CargoDTO(cargo.getId(), cargo.getDescription(), cargo.getWeight(), cargo.getStatus().toString(), cityDTO_from, cityDTO_to/*, orderDTO*/);
    }

    public static List<CargoDTO> getCargoDtos(List<Cargo> cargoList) {
        List<CargoDTO> cargoDTOResultList = new ArrayList<>();
        for(Cargo cargo : cargoList) {
            if (cargo.getOrder() == null) {
                cargoDTOResultList.add(Converter.getCargoDto(cargo));
            }
        }
        return cargoDTOResultList;
    }

    public static WagonDTO getWagonDto(Wagon wagon) {
        City current_city = wagon.getCurrent_city();
        CityDTO current_cityDTO = new CityDTO(current_city.getId(), current_city.getName(), current_city.getLongitude(), current_city.getLatitude());
        return new WagonDTO(wagon.getId(), wagon.getCar_plate(), wagon.getDriver_shift_count(), wagon.getCapacity(),
                wagon.getState().toString(), current_cityDTO);
    }

    public static List<WagonDTO> getWagonDtos(List<Wagon> wagonList) {
        List<WagonDTO> wagonDTOResultList = new ArrayList<>();
        for(Wagon wagon : wagonList) {
            wagonDTOResultList.add(Converter.getWagonDto(wagon));
        }
        return wagonDTOResultList;
    }

    public static DriverDTO getDriverDto(Driver driver, List<DriverShift> driverShiftList) {
        City current_cityDriver = driver.getCurrent_city();
        CityDTO current_cityDTO = new CityDTO(current_cityDriver.getId(), current_cityDriver.getName(), current_cityDriver.getLongitude(), current_cityDriver.getLatitude());
        Double hoursWorkedThisMonth = 0d;
        Date currentMonthBegin = DateUtil.getCurrentMonthBegin();
        Date currentMonthEnd = DateUtil.getCurrentMonthEnd();
        for(DriverShift driverShift : driverShiftList) {
            if (driverShift.getDriver().getId() == driver.getId()) {
                Date begin = driverShift.getBegin();
                Date end = driverShift.getEnd();

                if((begin.getTime() < currentMonthBegin.getTime()) && (end.getTime() < currentMonthBegin.getTime())) {
                    hoursWorkedThisMonth = 0d;
                } else {
                    if(begin.getTime() < currentMonthBegin.getTime()) {
                        begin = currentMonthBegin;
                    }
                    if(end.getTime() > currentMonthEnd.getTime()) {
                        end = currentMonthEnd;
                    }
                    hoursWorkedThisMonth += DateUtil.diffInHours(begin, end);
                }
            }
        }
        return new DriverDTO(driver.getId(), driver.getFirst_name(), driver.getSecond_name(), driver.getPersonal_number(),
                driver.getStatus().toString(), current_cityDTO, hoursWorkedThisMonth);
    }

    public static List<DriverDTO> getDriverDtos(List<Driver> driverList, List<DriverShift> driverShiftList) {
        List<DriverDTO> driverDTOList = new ArrayList<>();
        for(Driver driver : driverList) {
            driverDTOList.add(Converter.getDriverDto(driver, driverShiftList));
        }
        return driverDTOList;
    }

    /*public static DriverShiftDTO getDriverShiftDTO(DriverShift driverShift) {
        Driver driver = driverShift.getDriver();
        City city = driver.getCurrent_city();
        CityDTO cityDTO = new CityDTO(city.getId(), city.getName(), city.getLongitude(), city.getLongitude());
        DriverDTO driverDTO = new DriverDTO(driver.getId(), driver.getFirst_name(), driver.getSecond_name(), driver.getPersonal_number(),
                driver.getStatus().toString(), cityDTO);
        return new DriverShiftDTO(driverShift.getId(), driverShift.getBegin(), driverShift.getEnd(), driverDTO);
    }

    public static List<DriverShiftDTO> getDriverShiftDtos(List<DriverShift> driverShiftList) {
        List<DriverShiftDTO> driverShiftDTOS = new ArrayList<>();
        for(DriverShift driverShift : driverShiftList) {
            driverShiftDTOS.add(Converter.getDriverShiftDTO(driverShift));
        }
        return driverShiftDTOS;
    }*/
}
