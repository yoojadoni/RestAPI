package com.example.RestApi.domain.shop;

import com.example.RestApi.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "shop")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shop extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "address_code", nullable = false)
    private String addressCode;

    @Column(name = "address_detail", nullable = false)
    private String addressDetail;

    @Builder
    public Shop(Long shopId, String shopName, String addressCode, String addressDetail) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.addressCode = addressCode;
        this.addressDetail = addressDetail;
    }

}
