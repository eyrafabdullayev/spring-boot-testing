# spring-boot-testing

## About

The application we're going to use is an API that provides some basic operations on an Employee Resource.

### Dependencies

``` java

<dependency>
	  <groupId>org.springframework.boot</groupId>
	  <artifactId>spring-boot-starter-test</artifactId>
	  <scope>test</scope>
	  <version>2.2.6.RELEASE</version>
</dependency>
<dependency>
	  <groupId>com.h2database</groupId>
	  <artifactId>h2</artifactId>
	  <scope>test</scope>
</dependency>

```
### Integration Testing With @DataJpaTest

We're going to create an entity which has id and name as its properties:

``` java

@Entity
	@Table(name = "employee")
	public class Employee {
	 
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	 
	    @Size(min = 3, max = 45)
	    private String name;
	 
	    // standard getters and setters, constructors
	}
  
```

And here is our repository - using Spring Data JPA

``` java

@Repository
	public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	 
	    Employee findByName(String name);
	 
	}

```

After that we're going to write our test:

``` java

@RunWith(SpringRunner.class)
	@DataJpaTest
	public class EmployeeRepositoryIntegrationTest {
	 
	    @Autowired
	    private TestEntityManager entityManager;
	 
	    @Autowired
	    private EmployeeRepository employeeRepository;
	 
	    @Test
	    public void whenFindByName_thenReturnEmployee() {
	    // given
	    Employee james = new Employee("James");
	    entityManager.persist(james);
	    entityManager.flush();
	 
	    // when
	    Employee found = employeeRepository.findByName(james.getName());
	 
	    // then
	    assertThat(found.getName())
	      .isEqualTo(james.getName());
	}
	 
	}

```


###  Mocking With @MockBean
  
Our Service class is dependent on Repository. So, to test Service layer, we don't need to know about how the persistence layer is implemented:

``` java

@Service
	public class EmployeeServiceImpl implements EmployeeService {
	 
	    @Autowired
	    private EmployeeRepository employeeRepository;
	 
	    @Override
	    public Employee getEmployeeByName(String name) {
	        return employeeRepository.findByName(name);
	    }
	}

```
And our test class

``` java

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
	    Employee alex = new Employee("alex");
	 
	    Mockito.when(employeeRepository.findByName(alex.getName()))
	      .thenReturn(alex);
	    }
      
      @Test
	    public void whenValidName_thenEmployeeShouldBeFound() {
	    String name = "alex";
	    Employee found = employeeService.getEmployeeByName(name);
	 
	     assertThat(found.getName())
	      .isEqualTo(name);
	    }
	}

```
To check the Service class, we need to have an instance of Service class so that we can @Autowire it in our test class. This configuration is achieved by using the @TestConfiguration annotation.

