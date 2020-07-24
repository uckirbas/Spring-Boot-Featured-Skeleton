package com.example.servicito.domains.requests.services;

import com.example.servicito.domains.requests.models.entities.MRequest;
import com.example.common.exceptions.exists.MRequestAlreadyExistsException;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.MRequestNotFoundException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import com.example.common.exceptions.nullpointer.NullException;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.Date;

public interface MRequestService {
    Page<MRequest> getAll(int page);

    Page<MRequest> findByStatus(String status, int page);

    Page<MRequest> getAllByUser(Long userId, int page);

    Page<MRequest> getAllByRequestedTo(Long userId, int page);
    Page<MRequest> getAllByAssignedEmployee(Long userId, int page);

    MRequest getOne(Long id) throws MRequestNotFoundException, ForbiddenException;

    MRequest save(MRequest request) throws InvalidException, MRequestAlreadyExistsException, ForbiddenException;

    MRequest changeStatus(Long id, String status, Long assignedEmployeeId, Date confirmedFrom) throws MRequestAlreadyExistsException, InvalidException, ForbiddenException, UserNotFoundException, MRequestNotFoundException;

    @Transactional
    MRequest update(MRequest request, Date rentFrom) throws InvalidException, MRequestAlreadyExistsException, ForbiddenException;

//    void changeStatus(Long requestId, String status, Long assignedEmployeeId) throws MRequestAlreadyExistsException, MRequestNotFoundException, UserNotFoundException, ForbiddenException, InvalidException;

    boolean exists(Long id);

    @Transactional
    MRequest fixAppointment(Long id, String status, Long employeeId, Date appointmentDate, String appointmentPlace) throws ForbiddenException, InvalidException, MRequestAlreadyExistsException, NullException, MRequestNotFoundException, UserNotFoundException;

    Page<MRequest> findByStatusAndUser(String status, String userType, String username, Integer page);
}
