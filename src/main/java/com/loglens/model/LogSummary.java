package com.loglens.model;

import java.util.List;

public class LogSummary {
    private int totalLogs;
    private List<LogEntry> entries;

    public int getTotalLogs() { return totalLogs; }
    public void setTotalLogs(int totalLogs) { this.totalLogs = totalLogs; }

    public List<LogEntry> getEntries() { return entries; }
    public void setEntries(List<LogEntry> entries) { this.entries = entries; }
}
