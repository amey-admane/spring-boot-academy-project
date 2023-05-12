package com.acrnome.academy_amey.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Employeeentity")
public class Employee {

	@Id
	private long employeeId;

	@Column
	private String employeeEmail;

	@Column
	private String employeeName;

	@Column
	private String employeeAddress;

}
