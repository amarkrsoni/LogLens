package com.loglens.controller;

import com.loglens.model.LogSummary;
import com.loglens.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * Analyze raw log text.
     * Request JSON example:
     * {
     *   "logs": "2025-10-25 10:45:01 ERROR PaymentService - Failed to process transaction\n..."
     * }
     */
    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeLogs(@RequestBody Map<String, Object> payload) {
        Object logsObj = payload.get("logs");
        if (logsObj == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "'logs' field is missing"));
        }

        List<String> logs;
        if (logsObj instanceof String) {
            // Single string, split by newline
            logs = List.of(((String) logsObj).split("\\r?\\n"));
        } else if (logsObj instanceof List) {
            // Already a list
            logs = (List<String>) logsObj;
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "'logs' must be a string or array of strings"));
        }

        if (logs.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "'logs' cannot be empty"));
        }

        LogSummary summary = logService.analyze(logs);
        return ResponseEntity.ok(summary);
    }


    /**
     * Simple health check
     */
    @GetMapping("/health")
    public String health() {
        return "LogLens is running!";
    }
}
