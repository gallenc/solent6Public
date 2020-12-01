/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.devops.traffic.platerecognition.test;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.activemq.command.ActiveMQTextMessage;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.devops.message.jms.SimpleJmsListener;
import org.solent.devops.message.jms.SimpleJmsSender;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import org.solent.devops.message.jms.JSONMessage;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 *
 * @author 4GILLK91 <4GILLK91@solent.ac.uk>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig-service-test.xml"})
public class JMSThreadTest {

    @Autowired
    private SimpleJmsSender sender;

    @Test
    public void testANPR() throws Exception {

        try {

            byte[] encoded = Files.readAllBytes(Paths.get("src/test/resources/testMessage.txt"));
            String json = new String(encoded, Charset.forName("UTF-8"));

            // Message in to system
            sender.send("Test.Input", json);

            String response;

            // Latch test continues as soon as a response is received
            // If no response after 10 seconds the test will fail
            TestConfig.inputListenerLatch.await(10, TimeUnit.SECONDS);
            response = TestConfig.inputListenerReceived;
            assertEquals(json, response);

            TestConfig.outputListenerLatch.await(10, TimeUnit.SECONDS);
            response = TestConfig.outputListenerReceived;
            ObjectMapper objectMapper = new ObjectMapper();
            JSONMessage jsonMessage = objectMapper.readValue(response, JSONMessage.class);
            assertEquals("PP587A0",jsonMessage.getNumberplate());
            assertEquals("",jsonMessage.getPhoto());

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Configuration
    public static class TestConfig {

        private static final CountDownLatch inputListenerLatch = new CountDownLatch(1);
        private static final CountDownLatch outputListenerLatch = new CountDownLatch(1);

        private static String inputListenerReceived;
        private static String outputListenerReceived;

        @Bean
        public static BeanPostProcessor listenerWrapper() {
            return new BeanPostProcessor() {

                @Override
                public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                    if (bean instanceof SimpleJmsListener) {
                        MethodInterceptor interceptor = new MethodInterceptor() {

                            @Override
                            public Object invoke(MethodInvocation invocation) throws Throwable {
                                Object result = invocation.proceed();
                                
                                // Destination is used to differentiate between the input and output listeners
                                String dest = ((SimpleJmsListener) bean).getDestination();
                                
                                if (invocation.getMethod().getName().equals("onMessage")) {
                                    // This is misleading as "dest" is *not* the current listeners location
                                    System.out.println("Proxy invoked from " + dest + " onMessage()");
                                    
                                    ActiveMQTextMessage response = (ActiveMQTextMessage) invocation.getArguments()[0];
                                    String text = response.getText();
                                    if ("Test.Output".equals(dest)) {
                                        inputListenerReceived = text;
                                        inputListenerLatch.countDown();
                                    } else if ("None".equals(dest)) {
                                        outputListenerReceived = text;
                                        outputListenerLatch.countDown();
                                    }
                                }
                                return result;
                            }

                        };
                        if (AopUtils.isAopProxy(bean)) {
                            ((Advised) bean).addAdvice(interceptor);
                            return bean;
                        } else {
                            ProxyFactory proxyFactory = new ProxyFactory(bean);
                            proxyFactory.addAdvice(interceptor);
                            return proxyFactory.getProxy();
                        }
                    } else {
                        return bean;
                    }
                }

            };
        }

    }

}
