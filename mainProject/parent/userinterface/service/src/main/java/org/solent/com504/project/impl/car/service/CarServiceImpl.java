/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.car.service;

import java.util.List;
import org.solent.com504.project.model.car.dao.CarDAO;
import org.solent.com504.project.model.car.dto.Car;
import org.solent.com504.project.model.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author joao-
 */
@Component("carService")
public class CarServiceImpl implements CarService{

    @Autowired
    private CarDAO carDao;
    
    
    @Override
    public Car findById(Long id) {
        return carDao.findById(id);
    }

    @Override
    public List<Car> findAll() {
       return carDao.findAll();
    }

    @Override
    public Car findByNumbePlate(String numberPlate) {
        return carDao.findByNumberPlate(numberPlate);
    }

    @Override
    public Car save(Car car) {
        return carDao.save(car);
    }

    @Override
    public void deleteById(Long id) {
        carDao.deleteById(id);
    }

    @Override
    public void delete(Car car) {
        carDao.delete(car);
    }

    @Override
    public void deleteAll() {
        carDao.deleteAll();
    }
    
}
