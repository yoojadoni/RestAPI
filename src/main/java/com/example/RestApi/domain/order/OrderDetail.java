package com.example.RestApi.domain.order;

import com.example.RestApi.domain.BaseTimeEntity;
import com.example.RestApi.domain.item.Item;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Table(name = "order_detail")
public class OrderDetail extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id" , nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private Long price;

    private int amount;

    @Builder
    public OrderDetail(Long id, Order order, Item item, Long price, int amount) {
        this.id = id;
        this.order = order;
        this.item = item;
        this.price = price;
        this.amount = amount;
    }

    // 양방향 연관관계 편의 메소드
    public void setOrder(Order order){
        if(this.order != null){
            this.order.getOrderDetail().remove(this);
        }
        this.order = order;
        if(!order.getOrderDetail().contains(this)) {
            order.getOrderDetail().add(this);
        }
    }
}