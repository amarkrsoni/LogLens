package com.loglens.model;

public class LogEntry {
    private String timestamp;
    private String level;
    private String service;
    private String message;
    private String raw;

    public LogEntry() {}

    public LogEntry(String timestamp, String level, String service, String message, String raw) {
        this.timestamp = timestamp;
        this.level = level;
        this.service = service;
        this.message = message;
        this.raw = raw;
    }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRaw() { return raw; }
    public void setRaw(String raw) { this.raw = raw; }
}
