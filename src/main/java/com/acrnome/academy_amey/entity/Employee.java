package com.acrnome.academy_amey.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Employee entity mapped to Employeeentity table
@Entity
@Table(name = "Employeeentity")
public class Employee {

	// Primary key
	@Id
	private long employeeId;

	// Employee email
	@Column
	private String employeeEmail;

	// Employee name
	@Column
	private String employeeName;

	// Employee address
	@Column
	private String employeeAddress;

	// Note: Getters and setters should be added here
	// Consider using Lombok @Data annotation for auto-generation
}
