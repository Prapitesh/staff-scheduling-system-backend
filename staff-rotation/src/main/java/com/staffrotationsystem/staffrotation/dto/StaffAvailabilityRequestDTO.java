package com.staffrotationsystem.staffrotation.dto;

import com.staffrotationsystem.staffrotation.enums.AvailabilityStatus;

import java.time.LocalDate;

public class StaffAvailabilityRequestDTO {

    private Long staffId;
    private LocalDate date;
    private AvailabilityStatus status;

    public Long getStaffId() {
        return staffId;
    }

    public LocalDate getDate() {
        return date;
    }

    public AvailabilityStatus getStatus() {
        return status;
    }
}