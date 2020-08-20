package com.example.testingspringboot;

import com.example.testingspringboot.dao.EmployeeRepository;
import com.example.testingspringboot.entity.Employee;
import com.example.testingspringboot.service.EmployeeService;
import com.example.testingspringboot.service.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplIntegrationTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    }

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        Employee james = new Employee("James");

        Mockito.when(employeeRepository.findByName(james.getName()))
                .thenReturn(james);
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "James";
        Employee found = employeeService.getEmployeeByName(name);

        assertThat(found.getName())
                .isEqualTo(name);
    }
}
