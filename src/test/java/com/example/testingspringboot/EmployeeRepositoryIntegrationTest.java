package com.example.testingspringboot;

import com.example.testingspringboot.dao.EmployeeRepository;
import com.example.testingspringboot.entity.Employee;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private EmployeeRepository employeeRepository;


	@Test
	public void whenFindByName_thenReturnEmployee() {
	    //given
        Employee james = new Employee("James");
        entityManager.persist(james);
        entityManager.flush();

        //when
        Employee found = employeeRepository.findByName(james.getName());

        //than
        assertThat(found.getName())
                .isEqualTo(james.getName());
	}

}
