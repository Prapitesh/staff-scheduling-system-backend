package com.staffrotationsystem.staffrotation.dto;

public class StaffShiftCountDTO {

    private String staffName;
    private Long totalShifts;

    public StaffShiftCountDTO(String staffName, Long totalShifts) {
        this.staffName = staffName;
        this.totalShifts = totalShifts;
    }

    public String getStaffName() {
        return staffName;
    }

    public Long getTotalShifts() {
        return totalShifts;
    }
}