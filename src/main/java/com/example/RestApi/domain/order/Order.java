package com.example.RestApi.domain.order;

import com.example.RestApi.domain.BaseTimeEntity;
import com.example.RestApi.domain.shop.Shop;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Table(name = "orders")
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;


    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    List<OrderDetail> orderDetail = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY ,cascade = CascadeType.REFRESH)
    @JsonIgnore
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private Long price;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatusEnum status;

    @Column(name = "address_code", columnDefinition = "VARCHAR(128)")
//    @ColumnDefault("")
    private String addressCode = "";

    @Column(name = "address_detail", columnDefinition = "VARCHAR(255)")
//    @ColumnDefault("")
    private String addressDetail;

    // 배달비
    @Column(name = "delivery_cost")
    private Long deliveryCost;

    // 배달완료된 시간
    @JsonFormat(pattern = "yyyyMMddHHmm")
    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    // 배달 도착 예정시간
    @JsonFormat(pattern = "yyyyMMddHHmm")
    @Column(name = "delivery_expc_time")
    private LocalDateTime deliveryExpcTime;
    @Builder
    public Order(Long id, List<OrderDetail> orderDetail, Shop shop, Long price, int totalQuantity, OrderStatusEnum status, String addressCode, String addressDetail, Long deliveryCost, LocalDateTime deliveryTime, LocalDateTime deliveryExpcTime) {
        this.id = id;
        if(orderDetail != null && orderDetail.size() > 0) {
            for (OrderDetail detail : orderDetail) {
                addOrderDetail(detail);
            }
        }
        this.shop = shop;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.status = status;
        this.addressCode = addressCode;
        this.addressDetail = addressDetail;
        this.deliveryCost = deliveryCost;
        this.deliveryTime = deliveryTime;
        this.deliveryExpcTime = deliveryExpcTime;
    }

    @PrePersist
    public void prePersist() {
        this.status = this.status == null ? OrderStatusEnum.ORDER_COMPLETE : this.status;
        this.addressCode = this.getAddressCode() == null? null : this.addressCode;
    }

    // 양방향 연관관계 편의 메소드
    public void addOrderDetail(OrderDetail orderDetail){
        this.orderDetail.add(orderDetail);
        if(orderDetail.getOrder() != this){
            orderDetail.setOrder(this);
        }
    }

    public void changeOrder(Order order){
        if(order.getOrderDetail().size() > 0) {
            this.orderDetail = order.getOrderDetail();
        }

        if(order.getShop().getShopId() != null)
            this.shop = order.getShop();

        if(order.getPrice() != null)
            this.price = order.getPrice();

        if(order.getTotalQuantity() != 0)
            this.totalQuantity = order.getTotalQuantity();

        if(!order.getStatus().equals("") && order.getStatus() != null)
            this.status = order.getStatus();

        if(order.getAddressCode() != null && !order.getAddressCode().equals(""))
            this.addressCode = order.getAddressCode();

        if(order.getAddressDetail() != null && !order.getAddressDetail().equals(""))
            this.addressDetail = order.getAddressDetail();

        if(order.getDeliveryCost() != null )
            this.deliveryCost = order.getDeliveryCost();

        if(order.getDeliveryTime() != null && !order.getDeliveryTime().equals(""))
            this.deliveryTime = order.getDeliveryTime();

        if(order.getDeliveryExpcTime() != null && !order.getDeliveryExpcTime().equals(""))
            this.deliveryExpcTime = order.getDeliveryExpcTime();
    }

}