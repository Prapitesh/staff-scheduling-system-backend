package com.staffrotationsystem.staffrotation.repository;

import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.entity.StaffShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StaffShiftAssignmentRepository extends JpaRepository<StaffShiftAssignment,Long> {
    Optional<StaffShiftAssignment> findByStaffAndShiftDate(Staff staff, LocalDate shiftDate);

}
