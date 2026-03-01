package com.staffrotationsystem.staffrotation.service;

import com.staffrotationsystem.staffrotation.entity.Shift;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.entity.StaffShiftAssignment;
import com.staffrotationsystem.staffrotation.enums.ShiftType;
import com.staffrotationsystem.staffrotation.exception.DuplicateResourceException;
import com.staffrotationsystem.staffrotation.exception.InvalidRotationException;
import com.staffrotationsystem.staffrotation.repository.ShiftRepository;
import com.staffrotationsystem.staffrotation.repository.StaffRepository;
import com.staffrotationsystem.staffrotation.repository.StaffShiftAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RotationService {

    private final ShiftRepository shiftRepository;
    private final StaffRepository staffRepository;
    private final StaffShiftAssignmentRepository assignmentRepository;

//    public void generateWeeklyRotation(LocalDate weekStart){
//
//
//        List<Staff> staffList = staffRepository.findAll()
//                .stream()
//                .filter(Staff::getActive)
//                .toList();
//
//        List<Shift> shifts = shiftRepository.findAll();
//
//        if(shifts.size()!=2){
//            throw new RuntimeException("Rotation requires 2 shifts");
//        }
//
//        Shift dayShift=shifts.stream()
//                .filter(s->s.getShiftType().name().equals("DAY"))
//                .findFirst().orElseThrow();
//        Shift nightShift=shifts.stream()
//                .filter(s->s.getShiftType().name().equals("NIGHT"))
//                .findFirst().orElseThrow();
//
//        int weekNumber=weekStart.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
//        boolean isEvenWeek=weekNumber%2==0;
//        for(int i=0;i<staffList.size();i++){
//            Staff staff=staffList.get(i);
//            Shift assignedShift;
//            if(isEvenWeek){
//                assignedShift=(i%2==0)?dayShift:nightShift;
//            }
//            else {
//                assignedShift=(i%2==0)?nightShift:dayShift;
//            }
//            StaffShiftAssignment assignment=StaffShiftAssignment.builder()
//                    .staff(staff)
//                    .shift(assignedShift)
//                    .shiftDate(weekStart)
//                    .build();
//            assignmentRepository.save(assignment);
//        }
//    }

    public void generateWeeklyRotation(LocalDate weekStart) {

        if (weekStart.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new InvalidRotationException("Rotation must start on Monday");
        }

        LocalDate weekEnd = weekStart.plusDays(6);

        boolean alreadyExists =
                assignmentRepository.existsByShiftDateBetween(weekStart, weekEnd);

        if (alreadyExists) {
            throw new DuplicateResourceException("Rotation already generated for this week");
        }

        List<Staff> staffList = staffRepository.findAll()
                .stream()
                .filter(Staff::getActive)
                .toList();

        Shift dayShift = shiftRepository.findByShiftType(ShiftType.DAY)
                .orElseThrow();

        Shift nightShift = shiftRepository.findByShiftType(ShiftType.NIGHT)
                .orElseThrow();

        int weekNumber =
                weekStart.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        boolean evenWeek = weekNumber % 2 == 0;

        for (Staff staff : staffList) {

            // 1️⃣ Decide base shift for Monday (weekly fairness)
            Shift baseShift;
            if (evenWeek) {
                baseShift = dayShift;
            } else {
                baseShift = nightShift;
            }

            // 2️⃣ Prevent same shift as previous week
            Optional<StaffShiftAssignment> lastAssignmentOpt =
                    assignmentRepository.findTopByStaffOrderByShiftDateDesc(staff);

            if (lastAssignmentOpt.isPresent()) {
                Shift lastShift = lastAssignmentOpt.get().getShift();

                if (lastShift.getId().equals(baseShift.getId())) {
                    baseShift = baseShift.equals(dayShift) ? nightShift : dayShift;
                }
            }

            // 3️⃣ Generate 7 days with strict alternation
            for (int day = 0; day < 7; day++) {

                LocalDate currentDate = weekStart.plusDays(day);

                Shift assignedShift;

                if (day % 2 == 0) {
                    assignedShift = baseShift;
                } else {
                    assignedShift = baseShift.equals(dayShift) ? nightShift : dayShift;
                }

                StaffShiftAssignment assignment =
                        StaffShiftAssignment.builder()
                                .staff(staff)
                                .shift(assignedShift)
                                .shiftDate(currentDate)
                                .build();

                assignmentRepository.save(assignment);
            }
        }
    }

//    public List<Shift> getAllRotation() {
//        return shiftRepository.findAll();
//    }
}
