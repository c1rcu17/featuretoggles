package com.example.ft.entity.toggle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "Toggle", description = "Represents a toggle")
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"name", "enabled"})
public class ToggleDto {

    @ApiModelProperty(value = "The name of the toggle")
    private String name;

    @ApiModelProperty(value = "The label of the toggle")
    private String label;

    @ApiModelProperty(value = "A simple description for the toggle")
    private String description;

    @ApiModelProperty(value = "Toggle status")
    private Boolean enabled;

}
