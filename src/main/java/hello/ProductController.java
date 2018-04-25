package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import hello.Product;
import hello.ProductRepository;
import java.util.List;

@Controller
@RequestMapping(path="/product")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private EntityManager entityManager;

  // curl -i -X GET -u foo:bar 'localhost:8080/product/getallbyrepo'
	@GetMapping(path="/getallbyrepo")
	public @ResponseBody Iterable<Product> GetAllProductsByRepo() {
		// This returns a JSON or XML with the employees
		return productRepository.findAll();
	}

	@GetMapping(path="/getallbyprocedure")
	public @ResponseBody List<Product> GetAllProductsByProcedure() {
		 StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("GetAllProducts");

		 query.execute();
		 return query.getResultList();
	}
}
