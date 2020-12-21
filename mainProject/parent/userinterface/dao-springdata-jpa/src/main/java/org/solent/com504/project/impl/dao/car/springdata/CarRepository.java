/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.car.springdata;

/**
 *
 * @author Afonso
 */
import java.util.List;
import java.util.Set;
import org.solent.com504.project.model.car.dto.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CarRepository extends JpaRepository<Car, Long> {
    
    @Query("select c from Car c where c.numberPlate = :numberPlate")
    public Car findByNumberPlate(@Param("numberPlate") String numberPlate);
    
}
