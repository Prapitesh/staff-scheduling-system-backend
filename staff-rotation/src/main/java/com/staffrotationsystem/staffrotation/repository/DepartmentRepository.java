package com.staffrotationsystem.staffrotation.repository;

import com.staffrotationsystem.staffrotation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}