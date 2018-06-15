package com.example.ft.entity.rule;

import com.example.ft.entity.application.Application;
import com.example.ft.entity.toggle.Toggle;
import com.example.ft.mqtt.MqttLog;
import com.example.ft.mqtt.MqttService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.transaction.Transactional;

@Component
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"pk", "trueValue", "falseValue"})
public class Rule {

    private static MqttService mqttService;

    private static MqttLog mqttLog;

    @EmbeddedId
    private RulePk pk;

    @ManyToOne
    @JoinColumn(name = "applicationId", insertable = false, updatable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "toggleId", insertable = false, updatable = false)
    private Toggle toggle;

    @NonNull
    private Boolean trueValue;

    @NonNull
    private Boolean falseValue;

    @Builder
    public Rule(Application application, Toggle toggle, Boolean trueValue, Boolean falseValue) {
        super();
        setApplication(application);
        setToggle(toggle);
        setTrueValue(trueValue);
        setFalseValue(falseValue);
    }

    @Transactional
    public Boolean getFinalStatus() {
        return getToggle().getEnabled() ? getTrueValue() : getFalseValue();
    }

    @Autowired
    public void init(MqttService mqttService, MqttLog mqttLog) {
        Rule.mqttService = mqttService;
        System.out.println("Initializing with dependency [" + mqttService + "]");
        Rule.mqttLog = mqttLog;
        System.out.println("Initializing with dependency [" + mqttLog + "]");
    }

    public void mqttNotify(Boolean finalStatus) {
        String topic = new StringBuilder()
                .append("applications/")
                .append(getApplication().getName())
                .append("/")
                .append(getApplication().getVersion())
                .append("/")
                .append(getToggle().getName())
                .toString();
        mqttService.send(topic, finalStatus.toString());
        mqttLog.addToLog(topic + " --> " + finalStatus.toString());
    }

    @PostPersist
    @PostUpdate
    private void onPostChange() {
        mqttNotify(getFinalStatus());
    }

    @PostRemove
    private void onPostRemove() {
        mqttNotify(false);
    }

    @PrePersist
    private void onPrePersist() {
        if (getPk() == null) {
            setPk(new RulePk(getApplication().getId(), getToggle().getId()));
        }
    }

}
