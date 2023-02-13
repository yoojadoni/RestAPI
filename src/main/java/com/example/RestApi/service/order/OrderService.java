package com.example.RestApi.service.order;

import com.example.RestApi.common.exception.CustomException;
import com.example.RestApi.common.statuscode.ErrorCode;
import com.example.RestApi.common.statuscode.StatusCodeEnum;
import com.example.RestApi.domain.order.Order;
import com.example.RestApi.dto.order.OrderDTO;
import com.example.RestApi.repository.order.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    /**
     * 주문 조회
     * @param id
     * @return
     */
    @EntityGraph(attributePaths = {"orderDetail"})
    public Order findById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

    public Page<OrderDTO.Response> findOrder(Pageable pageable) throws Exception {
        List<OrderDTO.Response> orderList = new ArrayList<>();
        try {
            Page<Order> orderPage = orderRepository.findWithPage(pageable);
            orderList = orderPage.getContent().stream().map(OrderDTO.Response::toDTO).collect(Collectors.toList());
            if(orderPage.isEmpty())
                throw new CustomException(ErrorCode.NO_DATA);
        } catch (CustomException e){
            throw new CustomException(e.getErrorCode());
        } catch (Exception e){
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
        return new PageImpl<OrderDTO.Response>(orderList, pageable, orderList.size());
    }

    /**
     * 주문 저장
     * @param order
     * @return
     * @throws Exception
     */
    @Transactional
    public Order save(Order order) throws Exception{
        Order result = Order.builder().build();
        try {
            result = orderRepository.save(order);
        }catch (CustomException e){
            throw new CustomException(e.getErrorCode());
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(ErrorCode.SERVER_ERROR.getMessage());
        }
        return result;
    }

    /**
     * 주문 수정
     * @param order
     * @return
     * @throws Exception
     */
    @Transactional
    public Order update(Order order) throws Exception{
        Order result = Order.builder().build();
        try{
            result = orderRepository.findById(order.getId()).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
            result.changeOrder(order);
        }catch (CustomException e){
            throw new CustomException(e.getErrorCode());
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
        return result;
    }

}
