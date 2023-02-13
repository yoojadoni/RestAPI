package com.example.RestApi.common.statuscode;

import lombok.Getter;

@Getter
public enum StatusCodeEnum {

    ORDER_CREATED_OK(210, "주문 생성 완료"),
    ORDER_FIND_OK(211, "주문 정보 조회 완료"),
    ORDER_UPDATE_OK(212, "주문 수정 완료");
    private final int code;
    private final String message;
    StatusCodeEnum(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}