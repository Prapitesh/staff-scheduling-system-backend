package com.staffrotationsystem.staffrotation.repository;

import com.staffrotationsystem.staffrotation.entity.LeaveRequest;
import com.staffrotationsystem.staffrotation.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByStaffIdAndStatus(Long staffId, LeaveStatus status);

    boolean existsByStaffIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(
            Long staffId,
            LocalDate date1,
            LocalDate date2,
            LeaveStatus status
    );
}