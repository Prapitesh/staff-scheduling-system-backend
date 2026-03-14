package com.staffrotationsystem.staffrotation.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConflictDetectionDTO {

    private long duplicateAssignments;

    private long staffOnLeaveAssigned;

    private long understaffedShifts;
}