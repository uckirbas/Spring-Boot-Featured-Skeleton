package com.example.servicito.domains.notifications.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.common.utils.DateUtil;
import com.example.servicito.domains.notifications.models.entities.FirebaseUserToken;
import com.example.servicito.domains.notifications.models.pojo.NotificationData;
import com.example.servicito.domains.notifications.models.pojo.PushNotification;
import com.example.servicito.domains.requests.models.entities.MRequest;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.unknown.UnknownException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${app.fcm.serverkey}")
    private String fcmServerKey;

    @Value("${app.config.landlordownagent}")
    private boolean isLandlordOwnAgent;

    private final FirebaseTokenService firebaseTokenService;

    @Autowired
    public NotificationServiceImpl(FirebaseTokenService firebaseTokenService) {
        this.firebaseTokenService = firebaseTokenService;
    }

    @Override
    public void sendNotification(Long userId, PushNotification notification) throws InvalidException, NotFoundException, JsonProcessingException, UnknownException {
        if (userId == null || notification == null)
            throw new InvalidException("Could not send notification, some item is null");
        FirebaseUserToken token = this.firebaseTokenService.get(userId);
        if (token == null) return;
        notification.setTo(token.getUserToken());
        String notiString = new ObjectMapper().writeValueAsString(notification);

        try {
            this.postData(notiString);
        } catch (IOException e) {
            throw new UnknownException(e.getMessage());
        }

    }

    @Override
    public void sendNotification(PushNotification notification) throws InvalidException, JsonProcessingException, UnknownException {
        if (notification == null) throw new InvalidException("Could not send notification, some item is null");
        String notiString = new ObjectMapper().writeValueAsString(notification);
        System.out.println(notiString);
        try {
            this.postData(notiString);
        } catch (IOException e) {
            throw new UnknownException(e.getMessage());
        }
    }

    private void postData(String notiString) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://fcm.googleapis.com/fcm/send");

        StringEntity entity = new StringEntity(notiString, "UTF-8");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", "key=" + this.fcmServerKey);
        CloseableHttpResponse response = client.execute(httpPost);
        client.close();
        response.close();
//        HttpEntity e = response.getEntity();
//        InputStream is = e.getContent();
    }

    private void sendNewRequestNotificationToRequestedTo(MRequest request, PushNotification.Type notificationType) throws UnknownException, InvalidException, NotFoundException, JsonProcessingException {
        NotificationData data = new NotificationData();
        data.setTitle("নতুন রিকোয়েস্ট অ্যালার্ট!");
        data.setMessage("আপনার একটি নতুন রিকোয়েস্ট এসেছে। রিকোয়েস্ট এক্সেপ্ট করলে যিনি রিকোয়েস্ট পাঠিয়েছেন তিনি আপনার ফোন নম্বর দেখতে পাবেন এবং আপনার সাথে যোগাযোগ করতে পারবেন।");
        data.setType(notificationType.getValue());
        data.setCode(200); // doesn't mean anything yet. will work on it later
        PushNotification notification = new PushNotification();
        notification.setData(data);
        this.sendNotification(request.getRequestedTo().getId(), notification);
    }

    private void sendNewRequestNotificationToLandlord(MRequest request, PushNotification.Type notificationType) throws UnknownException, InvalidException, NotFoundException, JsonProcessingException {
        NotificationData data = new NotificationData();
        data.setTitle("নতুন রিকোয়েস্ট অ্যালার্ট!");
        data.setMessage("আপনার একটি নতুন রিকোয়েস্ট এসেছে। রিকোয়েস্ট এক্সেপ্ট করলে যিনি রিকোয়েস্ট পাঠিয়েছেন তিনি আপনার ফোন নম্বর দেখতে পাবেন এবং আপনার সাথে যোগাযোগ করতে পারবেন।");
        data.setType(notificationType.getValue());
        data.setCode(200); // doesn't mean anything yet. will work on it later
        PushNotification notification = new PushNotification();
        notification.setData(data);
        this.sendNotification(request.getLandlordInfo().getId(), notification);
    }

    @Override
    public void sendAssignRequestAlertToFieldEmployee(MRequest request, PushNotification.Type notificationType) throws InvalidException, NotFoundException, JsonProcessingException, UnknownException {
        NotificationData data = new NotificationData();
        data.setTitle("নতুন রিকোয়েস্ট অ্যালার্ট!");
        data.setMessage("আপনার একটি নতুন রিকোয়েস্ট এসেছে। অনুগ্রহ করে \"আমার রিকোয়েস্ট\" পেইজে গিয়ে চেক করে দেখুন।");
        data.setType(notificationType.getValue());
        data.setCode(201); // doesn't mean anything yet. will work on it later
        PushNotification notification = new PushNotification();
        notification.setData(data);
        this.sendNotification(request.getAssignedEmployee().getId(), notification);
    }

    @Override
    public void sendReceivedNotificationToUser(MRequest request, PushNotification.Type notificationType) throws InvalidException, NotFoundException, JsonProcessingException, UnknownException {
        NotificationData data = new NotificationData();
        data.setTitle("অভিনন্দন!");
        data.setMessage("আপনার রিকোয়েস্টটি গ্রহণ করা হয়েছে। আপনি বাড়িওয়ালার সাথে যোগাযোগ করতে পারবেন। বাড়িওয়ালার ফোন নম্বর জানতে \'আমার পাঠানো রিকোয়েস্ট\' এ গিয়ে রিকোয়েস্টের উপর ট্যাপ করুন। ");
        data.setType(notificationType.getValue());
        data.setCode(202);
        PushNotification notification = new PushNotification();
        notification.setData(data);
        this.sendNotification(request.getRequestedBy().getId(), notification);
    }

    @Override
    public void sendAppointmentFixedNotificationToUser(MRequest request, PushNotification.Type notificationType) throws UnknownException, InvalidException, NotFoundException, JsonProcessingException {
        NotificationData data = new NotificationData();
        data.setTitle("অ্যাপয়েন্টমেন্ট ঠিক করা হয়েছে! -:- " + DateUtil.getReadableDate(request.getAppointment().getDate()));
        data.setMessage("আমাদের মাঠকর্মী " + DateUtil.getReadableTime(request.getAppointment().getDate()) + " সময়ে অ্যাপয়েন্টমেন্ট সেট করেছেন।");
        data.setType(notificationType.getValue());
        data.setCode(203);
        PushNotification notification = new PushNotification();
        notification.setData(data);
        this.sendNotification(request.getRequestedBy().getId(), notification);
    }

    @Override
    public void sendMarkedAsConfirmedNotificationToUser(MRequest request, PushNotification.Type notificationType) throws UnknownException, InvalidException, NotFoundException, JsonProcessingException {
        NotificationData data = new NotificationData();
        data.setTitle("নিশ্চিত করা হয়েছে!");
        data.setMessage("আপনার রিকোয়েস্ট #" + request.getId() + " নিশ্চিত করা হয়েছে।\n\n আমাদের সার্ভিস নেয়ার জন্য আপনাকে একান্ত ধন্যবাদ। আশা করি আমাদের সাথে থাকবেন।\n\n (যেকোন ধরনের অভিযোগে আমাদের হেল্পলাইনে যোগাযোগ করুন।");
        data.setType(notificationType.getValue());
        data.setCode(204);
        PushNotification notification = new PushNotification();
        notification.setData(data);
        this.sendNotification(request.getRequestedBy().getId(), notification);
    }

    @Override
    public void sendRequestCanceledNotificationToUser(MRequest request, PushNotification.Type notificationType) throws UnknownException, InvalidException, NotFoundException, JsonProcessingException {
        NotificationData data = new NotificationData();
        data.setTitle("রিকোয়েস্ট বাতিল হয়েছে");
        data.setMessage("আশা করছি পরবর্তী রিকোয়েস্টে আরও ভালো সার্ভিস দিতে পারবো।");
        data.setType(notificationType.getValue());
        data.setCode(205);
        PushNotification notification = new PushNotification();
        notification.setData(data);
        this.sendNotification(request.getRequestedBy().getId(), notification);
        if (this.isLandlordOwnAgent)
            this.sendNotification(request.getLandlordInfo().getId(), notification);
    }

    @Override
    public void sendNotificationsByRequestStatus(MRequest request) throws NotFoundException, InvalidException, UnknownException, JsonProcessingException {
        if (request == null) return;
        // send user notification according to status
        if (this.isLandlordOwnAgent) { // when landlord is considered as his own agent
            if (request.isPending()) {
                if (request.getRequestedTo() != null)
                    this.sendNewRequestNotificationToRequestedTo(request, PushNotification.Type.ALERT);
                else
                    this.sendNewRequestNotificationToLandlord(request, PushNotification.Type.ALERT);
            }
            if (request.isReceived()) {
                this.sendReceivedNotificationToUser(request, PushNotification.Type.ALERT);
            } else if (request.isAppointmentFixed())
                this.sendAppointmentFixedNotificationToUser(request, PushNotification.Type.ALERT);// send user
            else if (request.isMarkedAsConfirmed())
                this.sendMarkedAsConfirmedNotificationToUser(request, PushNotification.Type.ALERT);
            else if (request.isCanceled())
                this.sendRequestCanceledNotificationToUser(request, PushNotification.Type.ALERT);
        } else {
            if (request.isReceived()) {
                this.sendAssignRequestAlertToFieldEmployee(request, PushNotification.Type.ALERT);
                this.sendReceivedNotificationToUser(request, PushNotification.Type.GENERAL);
            } else if (request.isAppointmentFixed())
                this.sendAppointmentFixedNotificationToUser(request, PushNotification.Type.GENERAL);// send user
            else if (request.isMarkedAsConfirmed())
                this.sendMarkedAsConfirmedNotificationToUser(request, PushNotification.Type.ALERT);
            else if (request.isCanceled())
                this.sendRequestCanceledNotificationToUser(request, PushNotification.Type.GENERAL);
        }
    }


}
