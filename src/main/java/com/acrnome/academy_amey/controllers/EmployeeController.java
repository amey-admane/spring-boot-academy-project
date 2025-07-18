package com.acrnome.academy_amey.controllers;

/*
 * Author :- Amey Admane
 * File created with Extending JPA Repository
 */

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acrnome.academy_amey.dto.EmployeeResponseDTO;
import com.acrnome.academy_amey.entity.Employee;
import com.acrnome.academy_amey.services.EmployeeService;

/**
 * REST Controller for Employee Management Operations.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/employee")
	public ResponseEntity<EmployeeResponseDTO> getAllEmployeeData(){
		// Fetch all employee data through service layer
		var returnedData = employeeService.getAllEmployeeData();
		
		
		EmployeeResponseDTO responseData = new EmployeeResponseDTO(returnedData, null, null);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseData);
	}
	
	//Retrieves a specific employee by their ID.
	@GetMapping("/employee/{id}")
	public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@RequestParam(value = "id") Long employeeId){
		// Fetch specific employee data
		var returnedData = employeeService.getEmployeeById(employeeId);
		
		
		EmployeeResponseDTO responseData = new EmployeeResponseDTO(null, returnedData, null);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseData);
	}
	
	//Creates a new employee record in the database.
	@PostMapping("employee/")
	public ResponseEntity<EmployeeResponseDTO> addEmployeeData(@RequestBody Employee employeeDataToAdd){
		// Save employee data through service layer
		employeeService.saveEmployeeData(employeeDataToAdd);
		
		// Create metadata to indicate successful operation
		Map<String,String> meta = new HashMap<>();
		meta.put("row saved", "true");
		
		// Return response with metadata only
		EmployeeResponseDTO responseData = new EmployeeResponseDTO(null, null, meta);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseData);
	}
}
