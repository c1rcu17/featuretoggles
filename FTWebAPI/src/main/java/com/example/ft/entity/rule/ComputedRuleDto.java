package com.example.ft.entity.rule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "ComputedRule", description = "Represents a toggle rule as seen by an application")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(of = {"name", "enabled"})
public class ComputedRuleDto {

    @ApiModelProperty(value = "The name of the toggle")
    private String name;

    @ApiModelProperty(value = "Toggle status")
    private Boolean enabled;

    @ApiModelProperty(value = "Should the feature be enabled if the toggle is enabled")
    private Boolean trueValue;

    @ApiModelProperty(value = "Should the feature be enabled if the toggle is disabled")
    private Boolean falseValue;

    @ApiModelProperty(value = "Toggle computed status (after rules)")
    private Boolean finalStatus;

}
