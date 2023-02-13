package com.example.RestApi.repository.itemcategory;

import com.example.RestApi.domain.itemcategory.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

}
