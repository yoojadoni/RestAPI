package com.example.RestApi.controller;

import com.example.RestApi.common.statuscode.StatusCodeEnum;
import com.example.RestApi.domain.order.Order;
import com.example.RestApi.dto.order.OrderDTO;
import com.example.RestApi.dto.order.common.Response;
import com.example.RestApi.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 주문생성
     * @param orderDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/order")
    public ResponseEntity<Response> order(@RequestBody OrderDTO.Request orderDTO) throws Exception{
        Order order = orderDTO.toEntity();
        OrderDTO.Response result = new OrderDTO.Response(orderService.save(orderDTO.toEntity()));
        return ResponseEntity.status(HttpStatus.OK).body(new Response(StatusCodeEnum.ORDER_CREATED_OK, result));
    }

    @GetMapping(value = "/order/{id}")
    public ResponseEntity<Response> getOrder(@PathVariable Long id) throws Exception{
        OrderDTO.Response result = new OrderDTO.Response(orderService.findById(id));
        System.out.println(">>> id="+result.id);
        return ResponseEntity.status(HttpStatus.OK).body(new Response(StatusCodeEnum.ORDER_FIND_OK, result));
    }

    @GetMapping(value = "/order")
    public ResponseEntity<Response> getOrderList(Pageable pageable) throws Exception {
//        Page<Order> orderPage = orderService.findOrder(pageable);
        Page<OrderDTO.Response> orderList = orderService.findOrder(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new Response(StatusCodeEnum.ORDER_FIND_OK, orderList));
    }

    @PutMapping(value = "/order")
    public ResponseEntity<Response> updateOrder(@RequestBody OrderDTO.Request orderDTO) throws Exception {
        OrderDTO.Response result = new OrderDTO.Response(orderService.update(orderDTO.toEntity()));
        return ResponseEntity.status(HttpStatus.OK).body(new Response(StatusCodeEnum.ORDER_UPDATE_OK, result));
    }
}
