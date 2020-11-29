/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.car.dao;

import java.util.List;
import org.solent.com504.project.model.car.dto.Car;

/**
 *
 * @author ruipi
 */
public interface CarDAO {
    
    public Car findById(Long id);
    
    public Car findByNumberPlate(String numberPlate);
    
    public List<Car> findAll();
    
    public Car save(Car car);
    
    public void deleteById(long id);
    
    public void delete(Car car);
    
    public void deleteAll();
}
