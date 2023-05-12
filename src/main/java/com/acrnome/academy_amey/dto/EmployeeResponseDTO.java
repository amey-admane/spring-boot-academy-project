package com.acrnome.academy_amey.dto;

import java.util.List;
import java.util.Map;

import com.acrnome.academy_amey.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeResponseDTO {
	
	private List<Employee> dataList; 
	private Employee data;
	private Map<String,String> metaData;
	
}
