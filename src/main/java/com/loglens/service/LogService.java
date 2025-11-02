package com.loglens.service;

import com.loglens.model.LogEntry;
import com.loglens.model.LogSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private EmailService emailService;

    private static final String ALERT_EMAIL = "aaravkrrr@gmail.com";

    public LogSummary analyze(List<String> logs) {
        List<LogEntry> entries = new ArrayList<>();
        List<LogEntry> errorLogs = new ArrayList<>();

        for (String line : logs) {
            LogEntry entry = parseLogLine(line);
            if (entry != null) {
                entries.add(entry);

                // collect error logs
                if ("ERROR".equalsIgnoreCase(entry.getLevel())) {
                    errorLogs.add(entry);
                }
            }
        }

        // send consolidated email if any error found
        if (!errorLogs.isEmpty()) {
            String serviceName = errorLogs.get(0).getService(); // assume same service for batch
            String subject = "[LogLens] ERROR Alert - " + entries.size() + " logs analyzed";

            // send detailed alert with all error logs
            emailService.sendErrorAlert(ALERT_EMAIL, subject, errorLogs, serviceName);
        }

        // prepare summary
        LogSummary summary = new LogSummary();
        summary.setTotalLogs(entries.size());
        summary.setEntries(entries);
        return summary;
    }

    private LogEntry parseLogLine(String line) {
        try {
            String[] parts = line.split(" ", 4);
            if (parts.length < 4) return null;

            String timestamp = parts[0] + " " + parts[1];
            String level = parts[2];
            String[] serviceMessage = parts[3].split(" - ", 2);
            String service = serviceMessage[0].trim();
            String message = serviceMessage.length > 1 ? serviceMessage[1].trim() : "";

            return new LogEntry(timestamp, level, service, message, line);
        } catch (Exception e) {
            return null;
        }
    }
}
