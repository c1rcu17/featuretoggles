package com.example.ft.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "AuthResponse", description = "Represents an auth response")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"token"})
public class AuthResponseDto {

    @ApiModelProperty(value = "The JWT token")
    private String token;

}
