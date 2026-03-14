package com.staffrotationsystem.staffrotation.dto;

public class MonthlyShiftSummaryDTO {

    private String staffName;
    private long dayShifts;
    private long nightShifts;
    private long totalShifts;

    public MonthlyShiftSummaryDTO(String staffName, long dayShifts, long nightShifts, long totalShifts) {
        this.staffName = staffName;
        this.dayShifts = dayShifts;
        this.nightShifts = nightShifts;
        this.totalShifts = totalShifts;
    }

    public String getStaffName() {
        return staffName;
    }

    public long getDayShifts() {
        return dayShifts;
    }

    public long getNightShifts() {
        return nightShifts;
    }

    public long getTotalShifts() {
        return totalShifts;
    }
}