package com.acrnome.academy_amey.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acrnome.academy_amey.entity.Employee;
import com.acrnome.academy_amey.repository.EmployeeRepository;

@Service
public class EmployeeService {

	
	@Autowired
	private EmployeeRepository  employeeRepository;
	
	public List<Employee> getAllEmployeeData() {
		return employeeRepository.findAll();
	}
	
	public Employee getEmployeeById(long employeeId) {
		var datareturned = employeeRepository.findById(employeeId);
		 if(datareturned.isPresent()) {
			 return datareturned.get();
		 }
		return null; 
		
	}
	
	public void saveEmployeeData(Employee employeeData) {
		employeeRepository.save(employeeData);
	}
	
}
