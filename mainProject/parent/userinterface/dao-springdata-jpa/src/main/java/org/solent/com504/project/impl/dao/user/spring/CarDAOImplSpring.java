/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.user.spring;

/**
 *
 * @author Afonso
 */

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.solent.com504.project.impl.dao.user.springdata.CarRepository;
import org.solent.com504.project.model.user.dao.CarDAO;
import org.solent.com504.project.model.user.dto.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class CarDAOImplSpring implements CarDAO {
    @Autowired
    private CarRepository carRepository = null;
    
    @Override
    public Car findById(Long id) {
        Optional<Car> o = carRepository.findById(id);
        if (o.isPresent()) {
            return o.get();
        }
        return null;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteById(long id) {
        carRepository.deleteById(id);
    }

    @Override
    public void delete(Car car) {
        carRepository.delete(car);
    }

    @Override
    public void deleteAll() {
        carRepository.deleteAll();
    }

    @Override
    public Car findByNumberPlate(String numberPlate) {
        return carRepository.findByNumberPlate(numberPlate);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }
}
