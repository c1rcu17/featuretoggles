package com.example.ft.mqtt;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mqtt")
public class MqttController {

    @Autowired
    private MqttLog mqttLog;

    @ApiOperation(value = "Returns the mqtt log")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/log")
    @ResponseBody
    public String log() {
        return mqttLog.getLog();
    }

}
