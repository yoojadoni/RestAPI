package com.example.RestApi.domain.item;

import com.example.RestApi.domain.BaseTimeEntity;
import com.example.RestApi.domain.itemcategory.ItemCategory;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item")
@Getter
@DynamicInsert
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code")
    @JsonIgnore
    private ItemCategory itemCategory;

    @Column(name = "item_name", columnDefinition = "VARCHAR(100)")
    private String itemName;

    @Column(name = "price")
    private Long price;

    @Builder
    public Item(Long itemId, ItemCategory itemCategory, String itemName, Long price)
    {
        this.itemId = itemId;
        this.itemCategory = itemCategory;
        this.itemName = itemName;
        this.price = price;
    }

}