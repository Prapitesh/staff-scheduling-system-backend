package com.staffrotationsystem.staffrotation.service;

import com.staffrotationsystem.staffrotation.dto.StaffAvailabilityRequestDTO;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.entity.StaffAvailability;
import com.staffrotationsystem.staffrotation.repository.StaffAvailabilityRepository;
import com.staffrotationsystem.staffrotation.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffAvailabilityService {

    private final StaffAvailabilityRepository availabilityRepository;
    private final StaffRepository staffRepository;

    public StaffAvailability markAvailability(StaffAvailabilityRequestDTO request) {

        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        StaffAvailability availability = new StaffAvailability(
                request.getDate(),
                request.getStatus(),
                staff
        );

        return availabilityRepository.save(availability);
    }
    }