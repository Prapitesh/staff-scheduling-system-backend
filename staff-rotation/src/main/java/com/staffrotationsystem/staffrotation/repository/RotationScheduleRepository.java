package com.staffrotationsystem.staffrotation.repository;

import com.staffrotationsystem.staffrotation.entity.RotationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RotationScheduleRepository extends JpaRepository<RotationSchedule, Long> {
}