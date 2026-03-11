package com.staffrotationsystem.staffrotation.repository;

import com.staffrotationsystem.staffrotation.entity.StaffAvailability;
import com.staffrotationsystem.staffrotation.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StaffAvailabilityRepository extends JpaRepository<StaffAvailability, Long> {
 Optional<StaffAvailability> findByStaffIdAndDate(Long staffId, LocalDate date);

boolean existsByStaffIdAndDateAndStatus(Long staffId, LocalDate date, AvailabilityStatus status);
}