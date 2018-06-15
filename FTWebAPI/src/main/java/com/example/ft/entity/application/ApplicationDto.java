package com.example.ft.entity.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "Application", description = "Represents an application")
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"name", "version"})
public class ApplicationDto {

    @ApiModelProperty(value = "The name of the application")
    private String name;

    @ApiModelProperty(value = "The label of the application")
    private String label;

    @ApiModelProperty(value = "The version of the application")
    private String version;

    @ApiModelProperty(value = "The username for authentication")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "The password for authentication")
    private String password;

}
