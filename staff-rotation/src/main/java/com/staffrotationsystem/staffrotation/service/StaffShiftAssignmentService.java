package com.staffrotationsystem.staffrotation.service;


import com.staffrotationsystem.staffrotation.dto.ConflictDetectionDTO;
import com.staffrotationsystem.staffrotation.dto.MonthlyShiftSummaryDTO;
import com.staffrotationsystem.staffrotation.dto.SpecificStaffMemberDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftCountDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftSummaryDTO;
import com.staffrotationsystem.staffrotation.entity.RotationSchedule;
import com.staffrotationsystem.staffrotation.entity.Shift;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.entity.StaffShiftAssignment;
import com.staffrotationsystem.staffrotation.enums.AvailabilityStatus;
import com.staffrotationsystem.staffrotation.enums.LeaveStatus;
import com.staffrotationsystem.staffrotation.exception.AvailabilityStatusException;
import com.staffrotationsystem.staffrotation.exception.DuplicateResourceException;
import com.staffrotationsystem.staffrotation.exception.InactiveStaffException;
import com.staffrotationsystem.staffrotation.exception.InvalidRotationException;
import com.staffrotationsystem.staffrotation.exception.SkillRequirementException;
import com.staffrotationsystem.staffrotation.repository.LeaveRequestRepository;
import com.staffrotationsystem.staffrotation.repository.RotationScheduleRepository;
import com.staffrotationsystem.staffrotation.repository.ShiftRepository;
import com.staffrotationsystem.staffrotation.repository.StaffAvailabilityRepository;
import com.staffrotationsystem.staffrotation.repository.StaffRepository;
import com.staffrotationsystem.staffrotation.repository.StaffShiftAssignmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffShiftAssignmentService {

    private final StaffShiftAssignmentRepository assignmentRepository;

    private final StaffRepository staffRepository;

    private final ShiftRepository shiftRepository;

    private final StaffAvailabilityRepository availabilityRepository;

    private final RotationScheduleRepository scheduleRepository;

    private final LeaveRequestRepository  leaveRequestRepository;
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

        // ⭐ Skill based validation
        if (shift.getShiftType().name().equals("NIGHT")) {

            if (!staff.getSkills().contains("AWS")) {
                throw new SkillRequirementException(
                        "Night shift requires AWS skill"
                );
            }
        }

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

    public void generateRotation(LocalDate startDate, int days) {

        List<Staff> staffList = staffRepository.findByActiveTrue();
        List<Shift> shifts = shiftRepository.findAll();

        if (staffList.isEmpty() || shifts.isEmpty()) {
            throw new RuntimeException("Staff or shifts not configured");
        }

        for (int i = 0; i < days; i++) {

            LocalDate date = startDate.plusDays(i);

            for (int j = 0; j < staffList.size(); j++) {

                Staff staff = staffList.get(j);

                // Skip staff on leave
                if (staff.getStatus() == AvailabilityStatus.LEAVE) {
                    continue;
                }

                // Rotate shift
                Shift shift = shifts.get((i + j) % shifts.size());

                // Avoid duplicate assignment
                if (assignmentRepository.existsByStaffIdAndShiftDate(staff.getId(), date)) {
                    continue;
                }

                StaffShiftAssignment assignment = StaffShiftAssignment.builder()
                        .staff(staff)
                        .shift(shift)
                        .shiftDate(date)
                        .build();

                assignmentRepository.save(assignment);
            }
        }
    }

    public List<MonthlyShiftSummaryDTO> getMonthlyShiftSummary(int month, int year) {
        return assignmentRepository.getMonthlyShiftSummary(month, year);
    }

    public void generateNextMonthRotation() {

        LocalDate start = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        int days = start.lengthOfMonth();

        generateRotation(start, days);
    }

    @Transactional
    public void regenerateRotation(LocalDate startDate, int days) {

        LocalDate endDate = startDate.plusDays(days - 1);

        // delete existing assignments
        assignmentRepository.deleteByShiftDateBetween(startDate, endDate);

        // generate new rotation
        generateRotation(startDate, days);
    }

    @Transactional
    public StaffShiftAssignment reassignShift(Long assignmentId, Long newStaffId) {

        StaffShiftAssignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() -> new RuntimeException("Assignment not found"));

        Staff newStaff = staffRepository.findById(newStaffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        LocalDate shiftDate = assignment.getShiftDate();

        // check if staff already has shift that day
        if (assignmentRepository.existsByStaffIdAndShiftDate(newStaffId, shiftDate)) {
            throw new DuplicateResourceException("Staff already has a shift on this date");
        }

        assignment.setStaff(newStaff);

        return assignmentRepository.save(assignment);
    }

    @Transactional
    public void generateNextMonthSchedule(String generatedBy) {

        LocalDate startDate = LocalDate.now().withDayOfMonth(1).plusMonths(1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        RotationSchedule schedule = RotationSchedule.builder()
                .startDate(startDate)
                .endDate(endDate)
                .generatedBy(generatedBy)
                .createdAt(LocalDateTime.now())
                .build();

        scheduleRepository.save(schedule);

        List<Staff> staffList = staffRepository.findByActiveTrue();
        List<Shift> shifts = shiftRepository.findAll();
        if (staffList.isEmpty()) {
            throw new RuntimeException("No active staff available");
        }
        int staffIndex = 0;

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {

            for (Shift shift : shifts) {

                Staff staff = staffList.get(staffIndex % staffList.size());

                boolean onLeave = leaveRequestRepository
                        .existsByStaffIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(
                                staff.getId(), date, date, LeaveStatus.APPROVED);

                if (onLeave) {
                    staffIndex++;
                    continue;
                }


                if (assignmentRepository.existsByStaffIdAndShiftDate(staff.getId(), date)) {
                    staffIndex++;
                    continue;
                }

                StaffShiftAssignment assignment = StaffShiftAssignment.builder()
                        .staff(staff)
                        .shift(shift)
                        .shiftDate(date)
                        .schedule(schedule)
                        .build();

                assignmentRepository.save(assignment);

                staffIndex++;
            }
        }
    }

    public ConflictDetectionDTO detectConflicts() {

        Long duplicates = assignmentRepository.countDuplicateAssignments();
        Long leaveConflicts = assignmentRepository.countStaffAssignedOnLeave();
        Long understaffed = assignmentRepository.countUnderstaffedShifts();

        return ConflictDetectionDTO.builder()
                .duplicateAssignments(duplicates == null ? 0 : duplicates)
                .staffOnLeaveAssigned(leaveConflicts == null ? 0 : leaveConflicts)
                .understaffedShifts(understaffed == null ? 0 : understaffed)
                .build();
    }

    public String exportScheduleToCSV() {

        List<StaffShiftAssignment> assignments =
                assignmentRepository.findAllByOrderByShiftDateAsc();

        StringBuilder csv = new StringBuilder();

        // CSV Header
        csv.append("Staff Name,Shift Type,Shift Date\n");

        for (StaffShiftAssignment a : assignments) {

            csv.append(a.getStaff().getName()).append(",")
                    .append(a.getShift().getShiftType()).append(",")
                    .append(a.getShiftDate()).append("\n");
        }

        return csv.toString();
    }
}

