package org.solent.com504.project.impl.dao.user.springdata;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.car.springdata.CarRepository;
import org.solent.com504.project.model.car.dto.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    private Car generatedCar;

    @Before
    @Transactional
    public void setUp() throws Exception {
        generatedCar = carRepository.save(generateDummyCar());
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
        carRepository.deleteById(generatedCar.getId());
    }

    @Test
    @Transactional
    public void testFindByNumberPlate() {
        Car result = carRepository.findByNumberPlate(generatedCar.getNumberPlate());
        assertEquals(generatedCar.getNumberPlate(), result.getNumberPlate());
    }

    private Car generateDummyCar() {
        Car car = new Car();
        car.setModel("FORD");
        car.setNumberPlate("US-FD-1001");
        return car;
    }
}