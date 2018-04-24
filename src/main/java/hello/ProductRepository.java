package hello;

import org.springframework.data.repository.CrudRepository;

import hello.Product;

// This will be AUTO IMPLEMENTED by Spring into a Bean called employeeRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
