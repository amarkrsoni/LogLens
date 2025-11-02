package com.loglens.service;

import com.loglens.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Overloaded method for error log batch
    public void sendErrorAlert(String to, String subject, List<LogEntry> errorLogs, String serviceName) {
        try {
            StringBuilder body = new StringBuilder();
            body.append("Service: ").append(serviceName).append("\n\n");
            body.append("Error Logs:\n");

            for (LogEntry log : errorLogs) {
                body.append("- [").append(log.getTimestamp()).append("] ")
                    .append(log.getMessage()).append("\n");
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body.toString());
            mailSender.send(message);

            System.out.println("Error alert email sent to " + to);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

//    // Keep your simple one too if needed
//    public void sendErrorAlert(String to, String subject, String body) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(body);
//            mailSender.send(message);
//            System.out.println("Error alert email sent to " + to);
//        } catch (Exception e) {
//            System.err.println("Failed to send email: " + e.getMessage());
//        }
//    }
}
