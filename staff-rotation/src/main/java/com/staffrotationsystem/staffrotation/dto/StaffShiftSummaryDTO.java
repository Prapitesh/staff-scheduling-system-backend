package com.staffrotationsystem.staffrotation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StaffShiftSummaryDTO {

    private String staffName;
    private Long totalShifts;


}
