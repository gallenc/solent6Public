/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.car.service;

import java.util.List;
import org.solent.com504.project.model.car.dto.Car;

/**
 *
 * @author joao-
 */
public interface CarService {
    
    public Car findById(Long id);
    
    public List<Car> findAll();
    
    public Car findByNumbePlate(String numberPlate);
    
    public Car save(Car car);
    
    public void deleteById(Long id);
    
    public void delete(Car car);
    
    public void deleteAll();    
}
