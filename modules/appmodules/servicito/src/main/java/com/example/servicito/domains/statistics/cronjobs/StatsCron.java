package com.example.servicito.domains.statistics.cronjobs;

import com.example.common.utils.DateUtil;
import com.example.coreweb.domains.mail.services.MailService;
import com.example.servicito.domains.statistics.models.dtos.AdminStats;
import com.example.servicito.domains.statistics.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class StatsCron {
    private final StatsService statsService;
    private final MailService mailService;

    @Value("${admin.send-report}")
    private String sendAdminReport;

    @Value("${adminEmail}")
    private String adminEmail;

    @Value("${reportToEmails}")
    private String reportToEmails;

    @Autowired
    public StatsCron(StatsService statsService, MailService mailService) {
        this.statsService = statsService;
        this.mailService = mailService;

//        new Thread(()->{
//            try {
//                Thread.sleep(5000);
//                scheduleFixedDelayTask();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void scheduleFixedDelayTask() {
        boolean sendAdminReport = Boolean.parseBoolean(this.sendAdminReport);
        if (!sendAdminReport) return;

        DateUtil.Period period = DateUtil.getPeriod("today");
        AdminStats stats = this.statsService.getStatsInAPeriod(period);

        if (reportToEmails == null) return;
        String[] emailAddresses = reportToEmails.split(",");
        String emailBody = "Today's stats for #Servicito:\n\n" +
                "\nTotal Hits: " + stats.getNoOfHits() +
                "\nRegistered Users: " + stats.getNoOfUsers().toString() +
                "\nNo of Requests: " + stats.getNoOfRequests().toString() +
                "\nAdded Buildings: " + stats.getNoOfBuildings().toString() +
                "\nAdded Apartments: " + stats.getNoOfApartments().toString() +
                "\n\n--------------------------------------\n\n" +
                "\nTotal Users:" + stats.getTotalUsers() +
                "\n\nTotal Requests:" + stats.getTotalRequests() +
                "\n\nTotal Buildings:" + stats.getTotalBuildings() +
                "\n\n\nRegards,\nSystem";

        String subject = "Servicito Stats Today (" + DateUtil.getReadableDate(new Date()) + ")";
        this.mailService.sendEmail(adminEmail, emailAddresses, null, subject, emailBody);
    }

}
