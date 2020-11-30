package org.solent.com504.project.impl.dao.user.spring;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.car.spring.CarDAOImplSpring;
import org.solent.com504.project.model.car.dto.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class CarDAOImplSpringTest {

    @Autowired
    private CarDAOImplSpring carDAOImplSpring;

    private Car generatedCar;

    @Before
    @Transactional
    public void setUp() throws Exception {
        generatedCar = carDAOImplSpring.save(generateDummyCar());
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
        carDAOImplSpring.deleteById(generatedCar.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        Car result = carDAOImplSpring.findById(generatedCar.getId());
        assertNotNull(result);
        assertEquals(generatedCar.getId(), result.getId());
    }

    @Test
    @Transactional
    public void testSave() {
        Car savedCar = carDAOImplSpring.save(generateDummyCar());
        assertNotNull(savedCar);
        assertNotNull(savedCar.getId());

        carDAOImplSpring.deleteById(savedCar.getId()); // delete so that DB is not getting filled by dummy data
    }

    @Test
    @Transactional
    public void testFindByNumberPlate() {
        Car result = carDAOImplSpring.findByNumberPlate(generatedCar.getNumberPlate());
        assertNotNull(result);
        assertEquals(generatedCar.getNumberPlate(), result.getNumberPlate());
    }

    private Car generateDummyCar() {
        Car car = new Car();
        car.setModel("FORD");
        car.setNumberPlate("US-FD-1001");
        return car;
    }
}