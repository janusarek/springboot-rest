package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.Employee;
import hello.EmployeeRepository;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class EmployeeController {
	@Autowired // This means to get the bean called employeeRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private EmployeeRepository employeeRepository;

	// @Autowired
  // private DataSource dataSource;

  // curl -i -X GET -u user:password 'localhost:8080/demo/add?name=First&email=someemail@someemailprovider.com'
	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewEmployee (@RequestParam String name
			, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Employee n = new Employee();
		n.setName(name);
		n.setEmail(email);
		employeeRepository.save(n);
		return "Saved";
	}

  // curl -i -X GET -u user:password localhost:8080/demo/all
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Employee> getAllEmployees() {
		// This returns a JSON or XML with the employees
		return employeeRepository.findAll();
	}
}
