package com.example.servicito.domains.requests.services;

import com.example.servicito.domains.requests.models.entities.Appointment;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.nullpointer.NullException;

public interface AppointmentService {
    Appointment save(Appointment appointment) throws NullException, InvalidException;
}
