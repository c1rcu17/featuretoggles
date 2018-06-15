package com.example.ft.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "ServiceErrorOutput", description = "Service error output")
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"httpStatusCode", "httpReason", "message"})
public class ServiceErrorOutputDto {

    @ApiModelProperty(value = "HTTP reason")
    public String httpReason;

    @ApiModelProperty(value = "HTTP status code")
    public Integer httpStatusCode;

    @ApiModelProperty(value = "Error message")
    public String message;

    public static ServiceErrorOutputDto fromException(ServiceException e) {
        ServiceErrorOutputDto output = new ServiceErrorOutputDto();
        output.setHttpReason(e.getHttpReason());
        output.setHttpStatusCode(e.getHttpStatusCode());
        output.setMessage(e.getMessage());
        return output;
    }

}
