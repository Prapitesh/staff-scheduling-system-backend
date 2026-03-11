package com.staffrotationsystem.staffrotation.dto;

import com.staffrotationsystem.staffrotation.enums.ShiftType;

import java.time.LocalDate;

public class SpecificStaffMemberDTO {
    LocalDate shiftDate;
    ShiftType shiftType;

    public SpecificStaffMemberDTO(LocalDate shiftDate, ShiftType shiftType) {
        this.shiftDate = shiftDate;
        this.shiftType = shiftType;
    }

    public LocalDate getShiftDate() { return shiftDate; }
    public ShiftType getShiftType() { return shiftType; }
}
