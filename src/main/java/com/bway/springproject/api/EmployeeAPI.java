package com.bway.springproject.api;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bway.springproject.model.Employee;
import com.bway.springproject.model.Product;
import com.bway.springproject.repository.productRepository;
import com.bway.springproject.service.EmployeeService;

@RestController
public class EmployeeAPI {
	
	@Autowired
	private productRepository prepo; //product ko lagi banako
	
	@Autowired
	private EmployeeService empService;
	
	@GetMapping("/api/emp/list")
	public List<Employee> getAll() {
		
		return empService.getAllEmployee();
		
	}

	@PostMapping("/api/emp/add")
	public String add(@RequestBody Employee emp) {
		
		empService.addEmployee(emp);
		
		return "Added successfully";
	}
	
	
	@GetMapping("/api/emp/{id}")
	public Employee getOne(@PathVariable Long id) {
		
		return empService.getEmployeeById(id);
	}
	
	
	@PutMapping("/api/emp/update")
	public String update(@RequestBody Employee emp) {
		
		empService.updateEmployee(emp);
		return "Updated Successfully";
		
	}
	
	@DeleteMapping("/api/emp/delete/{id}")
	public String delete(@PathVariable Long id) {
		
		empService.deleteEmployee(id);
		
		return "Deleted Successfully";
	}
	
	
	@GetMapping("/api/emp/j2o")
	public String jsonToObjMapping() {
		
		RestTemplate rt = new RestTemplate();
		Employee emp = rt.getForObject("http://localhost:8080/api/emp/1", Employee.class);
		
		return "FirstName = "+ emp.getFname();
	}
	
	
	@GetMapping("/api/emp/ja2oa")
	public String jsonArrayToObjArray() {
		
		RestTemplate rt = new RestTemplate();
		Employee[] emps = rt.getForObject("http://localhost:8080/api/emp/list", Employee[].class);
		
		return "Company = "+ emps[0].getCompany();
	}
	
	
	
	
	
	@GetMapping("/api/pload")
	public String loadProducts() {
		
		RestTemplate rt = new RestTemplate();
		Product[] plist = rt.getForObject("https://fakestoreapi.com/products", Product[].class);
		
		prepo.saveAll(List.of(plist));
		
		return "Loaded successfully";
	}
	
	
	
	
}
