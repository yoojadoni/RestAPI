package com.example.RestApi.orderDoc;


import com.example.RestApi.RestApiApplication;
import com.example.RestApi.domain.item.Item;
import com.example.RestApi.domain.itemcategory.ItemCategory;
import com.example.RestApi.domain.order.Order;
import com.example.RestApi.domain.order.OrderDetail;
import com.example.RestApi.domain.order.OrderStatusEnum;
import com.example.RestApi.domain.shop.Shop;
import com.example.RestApi.dto.order.OrderDTO;
import com.example.RestApi.dto.order.OrderDetailDTO;
import com.example.RestApi.repository.itemcategory.ItemCategoryRepository;
import com.example.RestApi.repository.shop.ShopRepository;
import com.example.RestApi.service.item.ItemService;
import com.example.RestApi.service.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
@DisplayName("Order Rest Doc ??????")
public class OrderDoc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void before(WebApplicationContext ctx, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @BeforeEach
    public void setUpInitData(){
        Shop shop = Shop.builder()
                .addressCode("123123")
                .addressDetail("????????? XX??? XX???")
                .shopName("XX??????")
                .build();

        shopRepository.save(shop);

        ItemCategory itemCategory = ItemCategory.builder()
                .categoryDepth(1)
                .categoryName("??????/??????")
                .build();

        ItemCategory itemCategory1 = ItemCategory.builder()
                .categoryDepth(2)
                .categoryName("??????")
                .build();

        ItemCategory itemCategory2 = ItemCategory.builder()
                .categoryDepth(3)
                .categoryName("??????/??????/??????")
                .build();

        ItemCategory itemCategory3 = ItemCategory.builder()
                .categoryDepth(3)
                .categoryName("?????????/?????????/??????/??????")
                .build();

        ItemCategory itemCategory4 = ItemCategory.builder()
                .categoryDepth(3)
                .categoryName("??????/??????")
                .build();

        itemCategoryRepository.save(itemCategory);
        itemCategoryRepository.save(itemCategory1);
        ItemCategory saveItemCategory =  itemCategoryRepository.save(itemCategory2);
        itemCategoryRepository.save(itemCategory3);
        itemCategoryRepository.save(itemCategory4);


        Item item = Item.builder()
                .itemCategory(saveItemCategory)
                .itemName("???????????? ??????(1+??????) 100G/?????????")
                .price(14590L)
                .build();
        item = itemService.save(item);
        
        Item item1 = Item.builder()
                .itemCategory(saveItemCategory)
                .itemName("???????????? ??????(1+??????) 100G/?????????")
                .price(10020L)
                .build();
        item1 = itemService.save(item1);

        List<Item> itemList = itemService.findAll();

        OrderDetail orderDetail = OrderDetail.builder()
                .price(itemList.get(0).getPrice())
                .amount(1)
                .item(itemList.get(0))
                .build();

        OrderDetail orderDetail1 = OrderDetail.builder()
                .price(itemList.get(1).getPrice())
                .amount(1)
                .item(itemList.get(1))
                .build();

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        orderDetailList.add(orderDetail1);

        Long price = 0L;

        for (OrderDetail detail : orderDetailList) {
            price += detail.getPrice();
        }

        Order order = Order.builder()
                .shop(shop)
                .price(price)
                .totalQuantity(2)
                .orderDetail(orderDetailList)
                .build();

        /*order.addOrderDetail(orderDetail);
        order.addOrderDetail(orderDetail1);*/

        OrderDetail orderDetail2 = OrderDetail.builder()
                .price(itemList.get(0).getPrice())
                .amount(1)
                .item(itemList.get(0))
                .build();

        OrderDetail orderDetail3 = OrderDetail.builder()
                .price(itemList.get(1).getPrice())
                .amount(1)
                .item(itemList.get(1))
                .build();

        List<OrderDetail> orderDetailList1 = new ArrayList<>();
        orderDetailList1.add(orderDetail2);
        orderDetailList1.add(orderDetail3);

        Order order1 = Order.builder()
                .shop(shop)
                .price(4000L)
                .orderDetail(orderDetailList1)
                .totalQuantity(2)
                .build();


        try {
            orderService.save(order);
            orderService.save(order1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("????????????")
    @Transactional
    public void ????????????(){
        try{
            //given
            Shop shop = Shop.builder()
                    .shopId(1L)
                    .build();

            List<Item> itemList = itemService.findAll();

            OrderDetailDTO orderDetailDTO = OrderDetailDTO.builder()
                    .price(itemList.get(0).getPrice())
                    .amount(1)
                    .itemId(itemList.get(0).getItemId())
                    .build();

            OrderDetailDTO orderDetailDTO1 = OrderDetailDTO.builder()
                    .price(itemList.get(1).getPrice())
                    .amount(1)
                    .itemId(itemList.get(1).getItemId())
                    .build();

            List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
            orderDetailDTOList.add(orderDetailDTO);
            orderDetailDTOList.add(orderDetailDTO1);

            Long price = 0L;
            for (OrderDetailDTO detailDTO : orderDetailDTOList) {
                price += detailDTO.getPrice();
            }

            OrderDTO.Request orderDTO = OrderDTO.Request.builder()
                    .shopId(shop.getShopId())
                    .price(price)
                    .totalQuantity(2)
                    .orderDetail(orderDetailDTOList)
                    .build();

            mockMvc.perform(RestDocumentationRequestBuilders.post("/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(orderDTO))
                    )
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andDo(document("order-create"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , requestFields(
                                    fieldWithPath("price").description("??? ??????")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("totalQuantity").description("????????? ????????? ??? ??????")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("shopId").description("????????? ?????? ID(key)")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].price").description("?????? ??????")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].amount").description("?????? ??????")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].itemId").description("?????? ?????????(MENU KEY)")
                                            .type(JsonFieldType.NUMBER)

                            )
                            , responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????(??????,??????,?????????)")
                                    , fieldWithPath("code").type(JsonFieldType.NUMBER).description("?????? ??????")
                                    , fieldWithPath("count").type(JsonFieldType.NUMBER).description("?????? ????????? ???")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("???????????????(KEY)")
                                    ,fieldWithPath("data.shopId")
                                            .type(JsonFieldType.NUMBER).description("????????? ?????? ID(key)")
                                    ,fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("??? ??????")
                                    ,fieldWithPath("data.totalQuantity")
                                            .type(JsonFieldType.NUMBER).description("????????? ????????? ??? ??????")
                                    ,fieldWithPath("data.status")
                                            .type(JsonFieldType.STRING).description("????????????")
                                    ,fieldWithPath("data.orderDetail.[].id")
                                            .type(JsonFieldType.NUMBER).description("?????? ??????ID")
                                    ,fieldWithPath("data.orderDetail.[].itemId")
                                            .type(JsonFieldType.NUMBER).description("???????????????")
                                    ,fieldWithPath("data.orderDetail.[].price")
                                            .type(JsonFieldType.NUMBER).description("????????? ??????")
                                    ,fieldWithPath("data.orderDetail.[].amount")
                                            .type(JsonFieldType.NUMBER).description("?????? ??????")
                                    ,fieldWithPath("data.orderDetail.[].itemName")
                                            .type(JsonFieldType.STRING).description("?????? ???")
                                            .optional()
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("????????????")
    @Transactional
    public void ????????????(){

        try{
            mockMvc.perform(RestDocumentationRequestBuilders.get("/order/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andDo(document("order-get"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , pathParameters(
                                    parameterWithName("id").description("????????????")
                            )
                            , responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????(??????,??????,?????????)")
                                    , fieldWithPath("code").type(JsonFieldType.NUMBER).description("?????? ??????")
                                    , fieldWithPath("count").type(JsonFieldType.NUMBER).description("?????? ????????? ???")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("???????????????(KEY)")
                                    ,fieldWithPath("data.shopId")
                                            .type(JsonFieldType.NUMBER).description("????????? ?????? ID(key)")
                                    ,fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("??? ??????")
                                    ,fieldWithPath("data.totalQuantity")
                                            .type(JsonFieldType.NUMBER).description("????????? ????????? ??? ??????")
                                    ,fieldWithPath("data.status")
                                            .type(JsonFieldType.STRING).description("????????????")
                                    ,fieldWithPath("data.orderDetail.[].id")
                                            .type(JsonFieldType.NUMBER).description("?????? ??????ID")
                                    ,fieldWithPath("data.orderDetail.[].itemId")
                                            .type(JsonFieldType.NUMBER).description("?????? ?????????")
                                    ,fieldWithPath("data.orderDetail.[].price")
                                            .type(JsonFieldType.NUMBER).description("????????? ??????")
                                    ,fieldWithPath("data.orderDetail.[].amount")
                                            .type(JsonFieldType.NUMBER).description("?????? ??????")
                                    ,fieldWithPath("data.orderDetail.[].itemName")
                                            .type(JsonFieldType.STRING).description("?????? ???")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("????????????Page")
    @Transactional
    public void ????????????Page(){
        try{
            mockMvc.perform(RestDocumentationRequestBuilders.get("/order")
                            .param("page", "0")
                            .param("size", "10")
                            .param("sort", "createdDate,asc")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andDo(document("order-getAll"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????(??????,??????,?????????)")
                                    , fieldWithPath("code").type(JsonFieldType.NUMBER).description("?????? ??????")
                                    , fieldWithPath("count").type(JsonFieldType.NUMBER).description("?????? ????????? ???")
                                    , fieldWithPath("data.list.[].id")
                                            .type(JsonFieldType.NUMBER).description("???????????????(KEY)")
                                    ,fieldWithPath("data.list.[].shopId")
                                            .type(JsonFieldType.NUMBER).description("????????? ?????? ID(key)")
                                    ,fieldWithPath("data.list.[].price")
                                            .type(JsonFieldType.NUMBER).description("??? ??????")
                                    ,fieldWithPath("data.list.[].totalQuantity")
                                            .type(JsonFieldType.NUMBER).description("????????? ????????? ??? ??????")
                                    ,fieldWithPath("data.list.[].status")
                                            .type(JsonFieldType.STRING).description("????????????")
                                    ,fieldWithPath("data.list.[].orderDetail.[].id")
                                            .type(JsonFieldType.NUMBER).description("?????? ??????ID")
                                    ,fieldWithPath("data.list.[].orderDetail.[].itemId")
                                            .type(JsonFieldType.NUMBER).description("?????? ?????????")
                                    ,fieldWithPath("data.list.[].orderDetail.[].price")
                                            .type(JsonFieldType.NUMBER).description("????????? ??????")
                                    ,fieldWithPath("data.list.[].orderDetail.[].amount")
                                            .type(JsonFieldType.NUMBER).description("?????? ??????")
                                    ,fieldWithPath("data.list.[].orderDetail.[].itemName")
                                            .type(JsonFieldType.STRING).description("?????? ???")
                                    ,fieldWithPath("data.totalPage")
                                            .type(JsonFieldType.NUMBER).description("??? ????????????")
                                    ,fieldWithPath("data.totalCount")
                                            .type(JsonFieldType.NUMBER).description("?????? ???????????????")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("??????????????????")
    @Transactional
    public void ??????????????????(){
        try{
            //given
            Shop shop = Shop.builder()
                    .shopId(1L)
                    .build();

            List<Item> itemList = itemService.findAll();

            OrderDetailDTO orderDetailDTO = OrderDetailDTO.builder()
                    .id(1L)
                    .price(itemList.get(0).getPrice())
                    .amount(1)
                    .itemId(itemList.get(0).getItemId())
                    .build();

            OrderDetailDTO orderDetailDTO1 = OrderDetailDTO.builder()
                    .id(2L)
                    .price(itemList.get(1).getPrice())
                    .amount(1)
                    .itemId(itemList.get(1).getItemId())
                    .build();

            List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
            orderDetailDTOList.add(orderDetailDTO);
            orderDetailDTOList.add(orderDetailDTO1);

            Long price = 0L;
            for (OrderDetailDTO detailDTO : orderDetailDTOList) {
                price += detailDTO.getPrice();
            }

            OrderDTO.Request orderDTO = OrderDTO.Request.builder()
                    .id(1L)
                    .shopId(shop.getShopId())
                    .price(price)
                    .totalQuantity(2)
                    .status(OrderStatusEnum.ORDER_CANCEL)
                    .orderDetail(orderDetailDTOList)
                    .build();

            mockMvc.perform(RestDocumentationRequestBuilders.put("/order")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(orderDTO))
                    )
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andDo(document("order-update"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , requestFields(
                                    fieldWithPath("id").description("?????? ??????")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("status").description("?????? ??????")
                                            .type(JsonFieldType.STRING)
                                    , fieldWithPath("price").description("??? ??????")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("totalQuantity").description("????????? ????????? ??? ??????")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("shopId").description("????????? ?????? ID(key)")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].id").description("?????? ?????? ID")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].price").description("?????? ??????")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].amount").description("?????? ??????")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].itemId").description("?????? ?????????(MENU KEY)")
                                            .type(JsonFieldType.NUMBER)

                            )
                            , responseFields(
                                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("?????? ??????(??????,??????,?????????)")
                                    , fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????(??????,??????,?????????)")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("???????????????(KEY)")
                                    ,fieldWithPath("data.shopId")
                                            .type(JsonFieldType.NUMBER).description("????????? ?????? ID(key)")
                                    ,fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("??? ??????")
                                    ,fieldWithPath("data.totalQuantity")
                                            .type(JsonFieldType.NUMBER).description("????????? ????????? ??? ??????")
                                    ,fieldWithPath("data.status")
                                            .type(JsonFieldType.STRING).description("????????????")
                                    ,fieldWithPath("data.orderDetail.[].id")
                                            .type(JsonFieldType.NUMBER).description("?????? ?????? ID")
                                    ,fieldWithPath("data.orderDetail.[].itemId")
                                            .type(JsonFieldType.NUMBER).description("???????????????")
                                    ,fieldWithPath("data.orderDetail.[].price")
                                            .type(JsonFieldType.NUMBER).description("????????? ??????")
                                    ,fieldWithPath("data.orderDetail.[].amount")
                                            .type(JsonFieldType.NUMBER).description("?????? ??????")
                                    , fieldWithPath("count").type(JsonFieldType.NUMBER).description("????????? ???")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
