package com.staffrotationsystem.staffrotation.repository;

import com.staffrotationsystem.staffrotation.dto.SpecificStaffMemberDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftCountDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftSummaryDTO;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.entity.StaffShiftAssignment;
import com.staffrotationsystem.staffrotation.enums.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StaffShiftAssignmentRepository extends JpaRepository<StaffShiftAssignment,Long> {
    Optional<StaffShiftAssignment> findByStaffAndShiftDate(Staff staff, LocalDate shiftDate);

    boolean existsByShiftDateBetween(LocalDate start, LocalDate end);

    Optional<StaffShiftAssignment>
    findTopByStaffOrderByShiftDateDesc(Staff staff);

    List<StaffShiftAssignment>
    findByStaffIdAndShiftDateBetweenOrderByShiftDateAsc(Long staffId, LocalDate weekStart, LocalDate weekEnd);

   boolean existsByStaffIdAndShiftDate(Long staffId,LocalDate shiftDate);

    List<StaffShiftAssignment> findByShiftDate(LocalDate shiftDate);


    @Query("""
SELECT new com.staffrotationsystem.staffrotation.dto.StaffShiftCountDTO(a.staff.name, COUNT(a))
FROM StaffShiftAssignment a
GROUP BY a.staff.name
HAVING COUNT(a) >= :minShifts
""")
    List<StaffShiftCountDTO> getStaffMorethanXShifts(Long minShifts);

    @Query("""
SELECT new com.staffrotationsystem.staffrotation.dto.StaffShiftCountDTO(
        a.staff.name,
        COUNT(a)
)
FROM StaffShiftAssignment a
GROUP BY a.staff.name
""")
    List<StaffShiftCountDTO> getShiftCountPerStaff();


    @Query("""
SELECT new com.staffrotationsystem.staffrotation.dto.StaffShiftCountDTO
       (a.staff.name, COUNT(a))
FROM StaffShiftAssignment a 
GROUP BY a.staff.name
ORDER BY COUNT(a) DESC 
""")
    List<StaffShiftCountDTO> getStaffByShiftCountDesc();

    // get all shift assignments of specific staff member
    @Query("""
SELECT new com.staffrotationsystem.staffrotation.dto.SpecificStaffMemberDTO(
        a.shiftDate,
        a.shift.shiftType
)
FROM StaffShiftAssignment a
WHERE a.staff.id = :staffId
""")
    List<SpecificStaffMemberDTO> findByStaffId(Long staffId);
    //get all shift assignments between two dates

    @Query("""
SELECT new com.staffrotationsystem.staffrotation.dto.StaffShiftDTO(
    a.staff.name,
    a.shiftDate,
    a.shift.shiftType
)
FROM StaffShiftAssignment a
WHERE a.shiftDate BETWEEN :start AND :end
""")
    List<StaffShiftDTO> findAssignmentsBetweenDates(LocalDate start, LocalDate end);

    @Query("""
SELECT a.staff.name
FROM StaffShiftAssignment a
WHERE MONTH(a.shiftDate) = :month
AND YEAR(a.shiftDate) = :year
GROUP BY a.staff.name
HAVING COUNT(DISTINCT a.shift.shiftType) > 1
""")
    List<String> findStaffWorkingDifferentShiftsInMonth(int month, int year);
}