package com.staffrotationsystem.staffrotation.repository;

import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.entity.StaffShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StaffShiftAssignmentRepository extends JpaRepository<StaffShiftAssignment,Long> {
    Optional<StaffShiftAssignment> findByStaffAndShiftDate(Staff staff, LocalDate shiftDate);

    boolean existsByShiftDateBetween(LocalDate start, LocalDate end);

    Optional<StaffShiftAssignment>
    findTopByStaffOrderByShiftDateDesc(Staff staff);

    List<StaffShiftAssignment> findByShiftDateBetween(LocalDate start, LocalDate end);
}
