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
@DisplayName("Order Rest Doc 생성")
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
                .addressDetail("서울시 XX구 XX동")
                .shopName("XX지점")
                .build();

        shopRepository.save(shop);

        ItemCategory itemCategory = ItemCategory.builder()
                .categoryDepth(1)
                .categoryName("정육/계란")
                .build();

        ItemCategory itemCategory1 = ItemCategory.builder()
                .categoryDepth(2)
                .categoryName("한우")
                .build();

        ItemCategory itemCategory2 = ItemCategory.builder()
                .categoryDepth(3)
                .categoryName("등심/안심/구이")
                .build();

        ItemCategory itemCategory3 = ItemCategory.builder()
                .categoryDepth(3)
                .categoryName("국거리/불고기/다짐/잡체")
                .build();

        ItemCategory itemCategory4 = ItemCategory.builder()
                .categoryDepth(3)
                .categoryName("사골/곰탕")
                .build();

        itemCategoryRepository.save(itemCategory);
        itemCategoryRepository.save(itemCategory1);
        ItemCategory saveItemCategory =  itemCategoryRepository.save(itemCategory2);
        itemCategoryRepository.save(itemCategory3);
        itemCategoryRepository.save(itemCategory4);


        Item item = Item.builder()
                .itemCategory(saveItemCategory)
                .itemName("안심한우 등심(1+등급) 100G/소고기")
                .price(14590L)
                .build();
        item = itemService.save(item);
        
        Item item1 = Item.builder()
                .itemCategory(saveItemCategory)
                .itemName("안심한우 양지(1+등급) 100G/소고기")
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
    @DisplayName("주문생성")
    @Transactional
    public void 주문생성(){
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
                                    fieldWithPath("price").description("총 금액")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("totalQuantity").description("주문한 상품의 총 수량")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("shopId").description("주문한 지점 ID(key)")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].price").description("상품 가격")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].amount").description("상품 수량")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].itemId").description("상품 아이디(MENU KEY)")
                                            .type(JsonFieldType.NUMBER)

                            )
                            , responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드")
                                    , fieldWithPath("count").type(JsonFieldType.NUMBER).description("전체 데이터 수")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("주문아이디(KEY)")
                                    ,fieldWithPath("data.shopId")
                                            .type(JsonFieldType.NUMBER).description("주문한 지점 ID(key)")
                                    ,fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("총 금액")
                                    ,fieldWithPath("data.totalQuantity")
                                            .type(JsonFieldType.NUMBER).description("주문한 상품의 총 수량")
                                    ,fieldWithPath("data.status")
                                            .type(JsonFieldType.STRING).description("주문상태")
                                    ,fieldWithPath("data.orderDetail.[].id")
                                            .type(JsonFieldType.NUMBER).description("주문 상세ID")
                                    ,fieldWithPath("data.orderDetail.[].itemId")
                                            .type(JsonFieldType.NUMBER).description("메뉴아이디")
                                    ,fieldWithPath("data.orderDetail.[].price")
                                            .type(JsonFieldType.NUMBER).description("상품당 가격")
                                    ,fieldWithPath("data.orderDetail.[].amount")
                                            .type(JsonFieldType.NUMBER).description("상품 수량")
                                    ,fieldWithPath("data.orderDetail.[].itemName")
                                            .type(JsonFieldType.STRING).description("상품 명")
                                            .optional()
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("주문조회")
    @Transactional
    public void 주문조회(){

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
                                    parameterWithName("id").description("주문번호")
                            )
                            , responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드")
                                    , fieldWithPath("count").type(JsonFieldType.NUMBER).description("응답 데이터 수")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("주문아이디(KEY)")
                                    ,fieldWithPath("data.shopId")
                                            .type(JsonFieldType.NUMBER).description("주문한 지점 ID(key)")
                                    ,fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("총 금액")
                                    ,fieldWithPath("data.totalQuantity")
                                            .type(JsonFieldType.NUMBER).description("주문한 상품의 총 수량")
                                    ,fieldWithPath("data.status")
                                            .type(JsonFieldType.STRING).description("주문상태")
                                    ,fieldWithPath("data.orderDetail.[].id")
                                            .type(JsonFieldType.NUMBER).description("주문 상세ID")
                                    ,fieldWithPath("data.orderDetail.[].itemId")
                                            .type(JsonFieldType.NUMBER).description("상품 아이디")
                                    ,fieldWithPath("data.orderDetail.[].price")
                                            .type(JsonFieldType.NUMBER).description("상품당 가격")
                                    ,fieldWithPath("data.orderDetail.[].amount")
                                            .type(JsonFieldType.NUMBER).description("상품 수량")
                                    ,fieldWithPath("data.orderDetail.[].itemName")
                                            .type(JsonFieldType.STRING).description("상품 명")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("주문조회Page")
    @Transactional
    public void 주문조회Page(){
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
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드")
                                    , fieldWithPath("count").type(JsonFieldType.NUMBER).description("응답 데이터 수")
                                    , fieldWithPath("data.list.[].id")
                                            .type(JsonFieldType.NUMBER).description("주문아이디(KEY)")
                                    ,fieldWithPath("data.list.[].shopId")
                                            .type(JsonFieldType.NUMBER).description("주문한 지점 ID(key)")
                                    ,fieldWithPath("data.list.[].price")
                                            .type(JsonFieldType.NUMBER).description("총 금액")
                                    ,fieldWithPath("data.list.[].totalQuantity")
                                            .type(JsonFieldType.NUMBER).description("주문한 상품의 총 수량")
                                    ,fieldWithPath("data.list.[].status")
                                            .type(JsonFieldType.STRING).description("주문상태")
                                    ,fieldWithPath("data.list.[].orderDetail.[].id")
                                            .type(JsonFieldType.NUMBER).description("주문 상세ID")
                                    ,fieldWithPath("data.list.[].orderDetail.[].itemId")
                                            .type(JsonFieldType.NUMBER).description("상품 아이디")
                                    ,fieldWithPath("data.list.[].orderDetail.[].price")
                                            .type(JsonFieldType.NUMBER).description("상품당 가격")
                                    ,fieldWithPath("data.list.[].orderDetail.[].amount")
                                            .type(JsonFieldType.NUMBER).description("상품 수량")
                                    ,fieldWithPath("data.list.[].orderDetail.[].itemName")
                                            .type(JsonFieldType.STRING).description("상품 명")
                                    ,fieldWithPath("data.totalPage")
                                            .type(JsonFieldType.NUMBER).description("총 페이지수")
                                    ,fieldWithPath("data.totalCount")
                                            .type(JsonFieldType.NUMBER).description("전체 데이터의수")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("주문업데이트")
    @Transactional
    public void 주문업데이트(){
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
                                    fieldWithPath("id").description("주문 번호")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("status").description("주문 상태")
                                            .type(JsonFieldType.STRING)
                                    , fieldWithPath("price").description("총 금액")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("totalQuantity").description("주문한 상품의 총 수량")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("shopId").description("주문한 지점 ID(key)")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].id").description("주문 상세 ID")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].price").description("상품 가격")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].amount").description("상품 수량")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("orderDetail.[].itemId").description("상품 아이디(MENU KEY)")
                                            .type(JsonFieldType.NUMBER)

                            )
                            , responseFields(
                                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태 코드(성공,실패,오류등)")
                                    , fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("주문아이디(KEY)")
                                    ,fieldWithPath("data.shopId")
                                            .type(JsonFieldType.NUMBER).description("주문한 지점 ID(key)")
                                    ,fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("총 금액")
                                    ,fieldWithPath("data.totalQuantity")
                                            .type(JsonFieldType.NUMBER).description("주문한 상품의 총 수량")
                                    ,fieldWithPath("data.status")
                                            .type(JsonFieldType.STRING).description("주문상태")
                                    ,fieldWithPath("data.orderDetail.[].id")
                                            .type(JsonFieldType.NUMBER).description("주문 상세 ID")
                                    ,fieldWithPath("data.orderDetail.[].itemId")
                                            .type(JsonFieldType.NUMBER).description("상품아이디")
                                    ,fieldWithPath("data.orderDetail.[].price")
                                            .type(JsonFieldType.NUMBER).description("상품당 가격")
                                    ,fieldWithPath("data.orderDetail.[].amount")
                                            .type(JsonFieldType.NUMBER).description("상품 수량")
                                    , fieldWithPath("count").type(JsonFieldType.NUMBER).description("데이터 수")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
