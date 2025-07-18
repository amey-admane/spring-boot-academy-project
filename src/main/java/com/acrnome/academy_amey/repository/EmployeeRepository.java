package com.acrnome.academy_amey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acrnome.academy_amey.entity.Employee;

// Repository for Employee entity CRUD operations
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
