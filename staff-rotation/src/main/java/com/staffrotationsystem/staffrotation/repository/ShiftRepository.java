package com.staffrotationsystem.staffrotation.repository;

import com.staffrotationsystem.staffrotation.entity.Shift;
import com.staffrotationsystem.staffrotation.enums.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShiftRepository extends JpaRepository<Shift,Long> {
    Optional<Shift> findByShiftType(ShiftType shiftType);
}
