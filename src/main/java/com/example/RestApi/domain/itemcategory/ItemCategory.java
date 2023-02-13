package com.example.RestApi.domain.itemcategory;

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
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Table(name = "item_category")
public class ItemCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_depth", nullable = false)
    private int categoryDepth;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Builder
    public ItemCategory(Long categoryId, int categoryDepth, String categoryName) {
        this.categoryId = categoryId;
        this.categoryDepth = categoryDepth;
        this.categoryName = categoryName;
    }
}