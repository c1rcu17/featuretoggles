package com.example.ft.entity.rule;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString(of = {"toggleId", "applicationId"})
public class RulePk implements Serializable {

    @NonNull
    private Long applicationId;

    @NonNull
    private Long toggleId;

}
