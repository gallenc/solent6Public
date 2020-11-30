package org.solent.com504.project.impl.dao.user.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.car.spring.CarDAOImplSpring;
import org.solent.com504.project.impl.dao.car.springdata.CarRepository;
import org.solent.com504.project.model.car.dto.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class CarDAOImplSpringDeleteTest {

    @Autowired
    private CarDAOImplSpring carDAOImplSpring;

    @Autowired
    private CarRepository repository;

    @Test
    @Transactional
    public void testDeleteById() {
        Car car = carDAOImplSpring.save(generateDummyCar());
        carDAOImplSpring.deleteById(car.getId());
        assertNull(carDAOImplSpring.findById(car.getId()));
    }

    @Test
    @Transactional
    public void testDelete() {
        Car car = carDAOImplSpring.save(generateDummyCar());
        carDAOImplSpring.delete(car);
        assertNull(carDAOImplSpring.findById(car.getId()));
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        carDAOImplSpring.deleteAll();
        assertTrue(repository.findAll().isEmpty());
    }

    private Car generateDummyCar() {
        Car car = new Car();
        car.setModel("FORD");
        car.setNumberPlate("US-FD-1001");
        return car;
    }
}
