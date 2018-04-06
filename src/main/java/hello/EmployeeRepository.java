package hello;

import org.springframework.data.repository.CrudRepository;

import hello.Employee;

// This will be AUTO IMPLEMENTED by Spring into a Bean called employeeRepository
// CRUD refers Create, Read, Update, Delete

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
