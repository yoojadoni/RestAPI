package com.example.RestApi.dto.order;

import com.example.RestApi.domain.order.OrderDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailDTO {

    public Long id;
    public Long price;
    public int amount;
    public Long itemId;
    public String itemName;

    public OrderDetailDTO(OrderDetail orderDetail){
        this.id = orderDetail.getId();
        this.price = orderDetail.getPrice();
        this.amount = orderDetail.getAmount();
        this.itemId = orderDetail.getItem().getItemId();
        this.itemName = orderDetail.getItem().getItemName();
    }

    public List<OrderDetailDTO> orderDetail(List<OrderDetail> orderDetailList){
        List<OrderDetailDTO> ordersDetailDTOList = new ArrayList<>();
        orderDetailList.forEach(v-> {
            ordersDetailDTOList.add(new OrderDetailDTO(v));
        });
        return ordersDetailDTOList;
    }

}
