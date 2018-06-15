package com.example.ft.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthRequest", description = "Represents an auth request")
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"username", "password"})
public class AuthRequestDto {

    @ApiModelProperty(value = "The username/application name")
    private String username;

    @ApiModelProperty(value = "The password")
    private String password;

}
