package com.example.RestApi.domain.order;

public enum OrderStatusEnum {
    ORDER_COMPLETE("ORDER_COMPLETE", "주문 완료"),
    ORDER_CANCEL("ORDER_CANCEL", "주문 취소"),
    PAY_SUCCESS("PAY_SUCCESS", "결제 완료"),
    PAY_CANCEL("PAY_CANCEL", "결제 취소"),
    DELIVERY_READY("DELIVERY_READY", "배달 준비중"),
    DELIVERY_IN("DELIVERY_IN", "배달 중"),
    DELIVERY_COMPLETE("DELIVERY_COMPLETE", "배달 완료")
    ;

    private String code;
    private String name;

    OrderStatusEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
