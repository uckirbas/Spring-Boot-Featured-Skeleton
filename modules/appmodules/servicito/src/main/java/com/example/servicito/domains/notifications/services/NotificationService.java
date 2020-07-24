package com.example.servicito.domains.notifications.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.servicito.domains.notifications.models.pojo.PushNotification;
import com.example.servicito.domains.requests.models.entities.MRequest;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.unknown.UnknownException;

public interface NotificationService {
    void sendNotification(Long userId, PushNotification notification) throws InvalidException, NotFoundException, JsonProcessingException, UnknownException;

    void sendNotification(PushNotification notification) throws InvalidException, JsonProcessingException, UnknownException;

    void sendAssignRequestAlertToFieldEmployee(MRequest request, PushNotification.Type notificationType) throws InvalidException, NotFoundException, JsonProcessingException, UnknownException;

    void sendReceivedNotificationToUser(MRequest request, PushNotification.Type notificationType) throws InvalidException, NotFoundException, JsonProcessingException, UnknownException;

    void sendAppointmentFixedNotificationToUser(MRequest request, PushNotification.Type notificationType) throws UnknownException, InvalidException, NotFoundException, JsonProcessingException;

    void sendMarkedAsConfirmedNotificationToUser(MRequest request, PushNotification.Type notificationType) throws UnknownException, InvalidException, NotFoundException, JsonProcessingException;

    void sendRequestCanceledNotificationToUser(MRequest request, PushNotification.Type notificationType) throws UnknownException, InvalidException, NotFoundException, JsonProcessingException;

    void sendNotificationsByRequestStatus(MRequest request) throws NotFoundException, InvalidException, UnknownException, JsonProcessingException;
}
