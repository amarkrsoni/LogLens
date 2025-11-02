package com.loglens.util;

import com.loglens.model.LogEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple log parser using regex. Adjust PATTERN to match your log format.
 *
 * Example log lines supported:
 * 2025-10-25 10:45:01 ERROR PaymentService - Failed to process transaction: NullPointerException
 */
public class LogParser {

    // Basic pattern: timestamp (YYYY-MM-DD HH:MM:SS) LEVEL Service - Message
	private static final Pattern LINE_PATTERN = Pattern.compile(
		    "^(?<ts>\\d{4}-\\d{2}-\\d{2}[ T]\\d{2}:\\d{2}:\\d{2})\\s+" + // timestamp
		    "(?<level>ERROR|WARN|INFO|DEBUG|TRACE)\\s+" +                 // level
		    "(?<service>\\S+)\\s+(?<message>.*)$"                        // first word = service, rest = message
		);


    public static List<LogEntry> parseLines(List<String> logs) {
        List<LogEntry> entries = new ArrayList<>();
        if (logs == null || logs.isEmpty()) return entries;

        for (String line : logs) {
            if (line == null || line.trim().isEmpty()) continue;

            Matcher m = LINE_PATTERN.matcher(line.trim());
            if (m.find()) {
                String ts = m.group("ts");
                String level = m.group("level");
                String service = m.group("service");
                String message = m.group("message");
                entries.add(new LogEntry(ts, level, service, message, line));
            } else {
                // fallback: keep raw line as message with null fields
                entries.add(new LogEntry(null, null, null, null, line));
            }
        }
        return entries;
    }
}
