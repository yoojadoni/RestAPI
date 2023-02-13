package com.example.RestApi.dto.order.common;

import com.example.RestApi.common.statuscode.ErrorCode;
import lombok.*;

@AllArgsConstructor
@Builder
@Data
@ToString(of = {"code", "message"})
public class ErrorResponse {

    public int status;
    public String code;
    public String message;

    public ErrorResponse(ErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}
