package com.luv2code.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;


@RestController
@RequestMapping("/api")
public class CustomerRestController {
	
	//inject the customer service
	@Autowired
	private CustomerService customerService;
	
	//add a mapping for GET /customers
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		
		return customerService.getCustomers();
	}
	
	//add a mapping for GET /customers/{customerId}
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId){
		
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if(theCustomer==null){
			throw new CustomerNotFoundException("Customer Not Found(with id) :"+customerId);
		}
		
		return theCustomer;
	}
	
	//add a mapping for POST /customers
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer){
		//set the customer id explicitly to 0 in case if JSON  from the client has id, 
		//so to insert a new entry each time (bec of functionality of dao.saveOrUpdate())
		
		theCustomer.setId(0);
		
		customerService.saveCustomer(theCustomer);
		return theCustomer;
	}
	
	//add a mapping for PUT /customers
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer){
		
		customerService.saveCustomer(theCustomer);
		return theCustomer;
	}
	
	//add a mapping DELETE /customers/{customerId}
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId){
		//throw an exception if customer to be deleted is not existed
		
		Customer theCustomer = customerService.getCustomer(customerId);
		if(theCustomer==null){
			throw new CustomerNotFoundException("Customer Not Found(with Id) :"+customerId);
		}
		
		customerService.deleteCustomer(customerId);
		return "Deleted Customer with Id :"+customerId;
	}

}
