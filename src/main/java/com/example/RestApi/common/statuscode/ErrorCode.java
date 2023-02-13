package com.example.RestApi.common.statuscode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_PARAMETER(400, "I001", "Invalid Request Data"),

    NO_DATA(401, "N001", "DATA가 없습니다."),
    COUPON_EXPIRATION(410, "O001", "주문 생성 실패"),
    COUPON_NOT_FOUND(411, "O002", "주문 가격 오류"),
    ORDER_NOT_FOUND(420, "O420", "주문 정보 확인 실패(주문 정보 확인요망)"),

    SERVER_ERROR(500, "E500", "서버에러");


    private final int status;
    private final String code;
    private final String message;
    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}