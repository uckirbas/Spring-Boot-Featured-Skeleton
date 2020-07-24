package com.example.servicito.domains.requests.services;

import com.example.servicito.domains.requests.models.entities.Appointment;
import com.example.servicito.domains.requests.repositories.AppointmentRepository;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.nullpointer.NullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepo;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    @Override
    public Appointment save(Appointment appointment) throws NullException, InvalidException {
        this.validate(appointment);
        return this.appointmentRepo.save(appointment);
    }

    private void validate(Appointment appointment) throws NullException, InvalidException {
        if (appointment == null) throw new NullException("Appointment can not be null");
        if (appointment.getDate() == null) throw new InvalidException("Appointment date can't be empty");
    }
}
