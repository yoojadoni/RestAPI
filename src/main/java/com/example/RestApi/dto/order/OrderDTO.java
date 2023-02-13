package com.example.RestApi.dto.order;

import com.example.RestApi.domain.item.Item;
import com.example.RestApi.domain.order.Order;
import com.example.RestApi.domain.order.OrderDetail;
import com.example.RestApi.domain.order.OrderStatusEnum;
import com.example.RestApi.domain.shop.Shop;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {

        public Long id;
        public Long shopId;
        @Builder.Default
        public List<OrderDetailDTO> orderDetail = new ArrayList<>();
        public Long price;
        public int totalQuantity;
        public OrderStatusEnum status;
        public String addressCode;
        public String addressDetail;
        public Long deliveryCost;
        public LocalDateTime deliveryTime;
        public LocalDateTime deliveryExpcTime;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Request request = (Request) o;
            return totalQuantity == request.totalQuantity && Objects.equals(id, request.id) && Objects.equals(shopId, request.shopId) && Objects.equals(orderDetail, request.orderDetail) && Objects.equals(price, request.price) && status == request.status && Objects.equals(addressCode, request.addressCode) && Objects.equals(addressDetail, request.addressDetail);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, shopId, orderDetail, price, totalQuantity, status);
        }

        public Order toEntity(){
            Order order = Order.builder()
                    .id(id)
                    .shop(Shop.builder().shopId(shopId).build())
                    .price(price)
                    .totalQuantity(totalQuantity)
                    .status(status)
                    .orderDetail(new ArrayList<>())
                    .addressCode(addressCode)
                    .addressDetail(addressDetail)
                    .deliveryExpcTime(deliveryExpcTime)
                    .deliveryTime(deliveryTime)
                    .deliveryCost(deliveryCost)
                    .build();

            List<OrderDetail> orderDetailList = this.orderDetail.stream()
                    .map(e -> new OrderDetail(
                            e.getId()
                            , order
                            , Item.builder().itemId(e.getItemId()).build()
                            , e.getPrice()
                            , e.getAmount()
                            )
                    ).collect(Collectors.toList());
            for (OrderDetail orderDetail : orderDetailList) {
                order.addOrderDetail(orderDetail);
            }

            return order;

        }

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response{
        public Long id;
        public Long shopId;
        public Long price;
        public int totalQuantity;
        public OrderStatusEnum status;
        List<OrderDetailDTO> orderDetail = new ArrayList<OrderDetailDTO>();

        // Entity -> DTO 변환
        public Response(Order order){
            this.id = order.getId();
            this.price = order.getPrice();
            this.totalQuantity = order.getTotalQuantity();
            this.status = order.getStatus();
            this.shopId = order.getShop().getShopId();
            this.orderDetail = order.getOrderDetail().stream().map(OrderDetailDTO::new).collect(Collectors.toList());
        }
        public static Response toDTO(Order order) {
            return Response.builder()
                    .id(order.getId())
                    .price(order.getPrice())
                    .totalQuantity(order.getTotalQuantity())
                    .status(order.getStatus())
                    .shopId(order.getShop().getShopId())
                    .orderDetail(order.getOrderDetail().stream().map(OrderDetailDTO::new).collect(Collectors.toList()))
                    .build();
        }
    }
}
