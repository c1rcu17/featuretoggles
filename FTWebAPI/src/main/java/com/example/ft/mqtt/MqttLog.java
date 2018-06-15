package com.example.ft.mqtt;

import org.springframework.stereotype.Component;

@Component
public class MqttLog {

    private StringBuilder log;

    public MqttLog() {
        log = new StringBuilder();
    }

    public void addToLog(String line) {
        synchronized (log) {
            log.append(line + "\n");
        }
    }

    public String getLog() {
        String currentLog;

        synchronized (log) {
            currentLog = log.toString();
            log = new StringBuilder();
        }

        return currentLog;
    }

}
