package com.example.ft.entity.rule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "Rule", description = "Represents a mapping between an application and a toggle")
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"trueValue", "falseValue"})
public class RuleDto {

    @ApiModelProperty(value = "Should the feature be enabled if the toggle is enabled")
    private Boolean trueValue;

    @ApiModelProperty(value = "Should the feature be enabled if the toggle is disabled")
    private Boolean falseValue;

}
