package com.acrnome.academy_amey.dto;

import java.util.List;
import java.util.Map;

import com.acrnome.academy_amey.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// DTO for Employee API responses
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeResponseDTO {
	
	// List of employees for bulk responses
	private List<Employee> dataList; 
	
	// Single employee for individual responses
	private Employee data;
	
	// Additional response metadata
	private Map<String,String> metaData;
	
}
