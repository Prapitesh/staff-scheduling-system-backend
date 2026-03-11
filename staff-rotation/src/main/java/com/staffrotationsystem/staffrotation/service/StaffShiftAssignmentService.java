package com.staffrotationsystem.staffrotation.service;


import com.staffrotationsystem.staffrotation.dto.SpecificStaffMemberDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftCountDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftSummaryDTO;
import com.staffrotationsystem.staffrotation.entity.Shift;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.entity.StaffShiftAssignment;
import com.staffrotationsystem.staffrotation.enums.AvailabilityStatus;
import com.staffrotationsystem.staffrotation.exception.AvailabilityStatusException;
import com.staffrotationsystem.staffrotation.exception.DuplicateResourceException;
import com.staffrotationsystem.staffrotation.exception.InactiveStaffException;
import com.staffrotationsystem.staffrotation.exception.InvalidRotationException;
import com.staffrotationsystem.staffrotation.repository.ShiftRepository;
import com.staffrotationsystem.staffrotation.repository.StaffAvailabilityRepository;
import com.staffrotationsystem.staffrotation.repository.StaffRepository;
import com.staffrotationsystem.staffrotation.repository.StaffShiftAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffShiftAssignmentService {

    private final StaffShiftAssignmentRepository assignmentRepository;

    private final StaffRepository staffRepository;

    private final ShiftRepository shiftRepository;

    private final StaffAvailabilityRepository availabilityRepository;

//    public StaffShiftAssignment assignShift(Long shiftId, Long staffId, LocalDate shiftDate) {
//        Staff staff=staffRepository.findById(staffId).orElseThrow(()->new RuntimeException("Staff not found"));
//        if (!staff.getActive()) {
//            throw new InvalidRotationException("Inactive staff cannot be assigned");
//        }
//        if (assignmentRepository.existsByStaffIdAndShiftDate(staffId, shiftDate)) {
//            throw new DuplicateResourceException("Staff already assigned a shift on this date");
//        }
//        Shift shift=shiftRepository.findById(shiftId).orElseThrow(()->new RuntimeException("Shift not found"));
//        StaffShiftAssignment assignment=StaffShiftAssignment.builder()
//                        .staff(staff)
//                        .shift(shift)
//                        .shiftDate(shiftDate)
//                        .build();
//        return assignmentRepository.save(assignment);
//    }

    public StaffShiftAssignment assignShift(Long staffId, Long shiftId, LocalDate shiftDate) {

        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff Not Found"));

        if (!staff.getActive()) {
            throw new InactiveStaffException("Inactive staff cannot be assigned shifts");
        }

        if (staff.getStatus() == AvailabilityStatus.LEAVE) {
            throw new AvailabilityStatusException("Staff is on leave and cannot be assigned a shift");
        }
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift Not Found"));

        if (assignmentRepository.existsByStaffIdAndShiftDate(staffId, shiftDate)) {
            throw new DuplicateResourceException("Staff Shift Assignment Already Exists");
        }

        StaffShiftAssignment assignment = StaffShiftAssignment.builder()
                .staff(staff)
                .shift(shift)
                .shiftDate(shiftDate)
                .build();

        return assignmentRepository.save(assignment);
    }

    public List<StaffShiftAssignment> findAllStaffShiftAssignments() {
        return assignmentRepository.findAll();
    }

    //get weekly assignments for a staff
    public List<StaffShiftAssignment> getWeeklyAssignmentsForStaff(Long staffId, LocalDate weekStart) {
        if (weekStart.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new InvalidRotationException("Week must start on Monday");
        }
        LocalDate weekEnd = weekStart.plusDays(6);
        return assignmentRepository.findByStaffIdAndShiftDateBetweenOrderByShiftDateAsc(staffId,weekStart,weekEnd);
    }

    // update shift date of an assignment
    public StaffShiftAssignment updateshiftdateofanassignment(Long assignmentId,LocalDate shiftDate)
    {
        StaffShiftAssignment assignment=assignmentRepository.findById(assignmentId).orElseThrow(()->new RuntimeException("Assignment Not Found"));
//        New date cannot be in the past
        if(shiftDate.isBefore(LocalDate.now())){
            throw new InvalidRotationException("New date cannot be in the past");
        }
        //Staff should not already have another shift on that date
        Long staffId=assignment.getStaff().getId();
        if (assignmentRepository.existsByStaffIdAndShiftDate(staffId,shiftDate)) {
            throw new DuplicateResourceException("Staff already assigned shift on this date");
        }
        assignment.setShiftDate(shiftDate);
        return assignmentRepository.save(assignment);
    }

    //get all staff working on a specific date
    public List<StaffShiftAssignment> getAllStaffWorkingOnSpecificDate(LocalDate shiftDate){
        return assignmentRepository.findByShiftDate(shiftDate);
    }

    //get shift count per staff
    public List<StaffShiftCountDTO> countshiftsperstaff(){
        return assignmentRepository.getShiftCountPerStaff();
    }

    //get staff worked more than or equal to x shifts
    public List<StaffShiftCountDTO> getStaffMorethanXShifts(Long minShifts){
        return assignmentRepository.getStaffMorethanXShifts(minShifts);
    }

    //staff who has maximum shifts
    public List<StaffShiftCountDTO> getMostLoadedStaff() {
        return Collections.singletonList(assignmentRepository
                .getStaffByShiftCountDesc()
                .stream()
                .findFirst()
                .orElseThrow());
    }

    //swap Shift Between Two Staff
    public void swapshifts(Long assignmentId1,Long assignmentId2) {

        StaffShiftAssignment staffShiftAssignment1=assignmentRepository.findById(assignmentId1).orElseThrow(()->new RuntimeException("Assignment1 Not Found"));
        StaffShiftAssignment staffShiftAssignment2=assignmentRepository.findById(assignmentId2).orElseThrow(()->new RuntimeException("Assignment2 Not Found"));
        Shift temp=staffShiftAssignment1.getShift();
        staffShiftAssignment1.setShift(staffShiftAssignment2.getShift());
        staffShiftAssignment2.setShift(temp);
        assignmentRepository.save(staffShiftAssignment1);
        assignmentRepository.save(staffShiftAssignment2);
    }

//    get all shift assignments of a specific staff member.
      public List<SpecificStaffMemberDTO> getSpecificStaffMembers(Long staffId) {
        return assignmentRepository.findByStaffId(staffId);
      }

    //get all shift assignments between two dates.
    public List<StaffShiftDTO> getallassignmentsbetweentwodates(LocalDate start, LocalDate end) {
        return assignmentRepository. findAssignmentsBetweenDates(start, end);
    }

    //get staff members
    public List<String> getStaffWorkingonDifferentShiftsinmonth(int month,int year){
        return assignmentRepository.findStaffWorkingDifferentShiftsInMonth(month,year);
    }
}

