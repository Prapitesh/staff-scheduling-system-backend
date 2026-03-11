package com.staffrotationsystem.staffrotation.repository;

import com.staffrotationsystem.staffrotation.dto.StaffWithoutShiftDTO;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.enums.StaffRole;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);

  List<Staff> findByActiveTrue();

  List<Staff> findByRoleAndActiveTrue(StaffRole staffRole);

  List<Staff> findByDepartmentAndActiveTrue(String  department);

  Page<Staff> findByActiveTrue(Pageable pageable);

    Page<Staff> findByRoleAndActiveTrue(StaffRole role, Pageable pageable);

   Page<Staff> findByRoleAndDepartmentIgnoreCaseAndActiveTrue(StaffRole role, String department, Pageable pageable);

    Page<Staff> findByDepartmentIgnoreCaseAndActiveTrue(String department,Pageable pageable);


  @Query("""
select new com.staffrotationsystem.staffrotation.dto.StaffWithoutShiftDTO(s.name)
from Staff s
left join StaffShiftAssignment a on s.id=a.staff.id
where a.id is NULL
""")
List<StaffWithoutShiftDTO> findStaffWithoutShifts();
}
