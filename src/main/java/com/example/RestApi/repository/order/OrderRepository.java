package com.example.RestApi.repository.order;

import com.example.RestApi.domain.order.Order;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>{
    @EntityGraph(attributePaths = {"orderDetail"})
    List<Order> findAll();

    @Query("select o from Order o JOIN FETCH o.orderDetail d JOIN FETCH d.item where o.id = :id")
    Optional<Order> findById(Long id);

    @BatchSize(size = 100)
    @Query(value =  "select distinct o from Order o JOIN FETCH o.orderDetail d JOIN FETCH o.shop s JOIN FETCH d.item "
            ,countQuery ="select count(o) from Order o"
    )
    Page<Order> findWithPage(Pageable pageable);

}
