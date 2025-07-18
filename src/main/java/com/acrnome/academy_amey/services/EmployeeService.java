package com.acrnome.academy_amey.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acrnome.academy_amey.entity.Employee;
import com.acrnome.academy_amey.repository.EmployeeRepository;

@Service
public class EmployeeService {

	// Repository for database operations
	@Autowired
	private EmployeeRepository employeeRepository;
	
	// Get all employees
	public List<Employee> getAllEmployeeData() {
		return employeeRepository.findAll();
	}
	
	// Get employee by ID
	public Employee getEmployeeById(long employeeId) {
		var dataReturned = employeeRepository.findById(employeeId);
		
		if(dataReturned.isPresent()) {
			return dataReturned.get();
		}
		
		return null; 
	}
	
	// Save or update employee
	public void saveEmployeeData(Employee employeeData) {
		employeeRepository.save(employeeData);
	}
	
}
