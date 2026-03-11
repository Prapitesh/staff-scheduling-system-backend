package com.staffrotationsystem.staffrotation.dto;

import com.staffrotationsystem.staffrotation.enums.ShiftType;
import java.time.LocalDate;

public class StaffShiftDTO {

    private String staffName;
    private LocalDate shiftDate;
    private ShiftType shiftType;

    public StaffShiftDTO(String staffName, LocalDate shiftDate, ShiftType shiftType) {
        this.staffName = staffName;
        this.shiftDate = shiftDate;
        this.shiftType = shiftType;
    }

    public String getStaffName() {
        return staffName;
    }

    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }
}