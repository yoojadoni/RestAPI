package com.example.RestApi;

import com.example.RestApi.domain.item.Item;
import com.example.RestApi.domain.itemcategory.ItemCategory;
import com.example.RestApi.domain.order.Order;
import com.example.RestApi.domain.order.OrderDetail;
import com.example.RestApi.domain.shop.Shop;
import com.example.RestApi.repository.itemcategory.ItemCategoryRepository;
import com.example.RestApi.repository.shop.ShopRepository;
import com.example.RestApi.service.item.ItemService;
import com.example.RestApi.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@EnableAspectJAutoProxy // AOP사용
@SpringBootApplication
public class RestApiApplication {

	@Autowired
	ItemService itemService;

	@Autowired
	OrderService orderService;

	@Autowired
	ShopRepository shopRepository;

	@Autowired
	ItemCategoryRepository itemCategoryRepository;
	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

	@PostConstruct
	public void initDataCreate(){
		Shop shop = Shop.builder()
				.addressCode("123123")
				.addressDetail("서울시 XX구 XX동")
				.shopName("XX지점")
				.build();

		shop = shopRepository.save(shop);

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
				.order(Order.builder().id(1L).build())
				.price(itemList.get(0).getPrice())
				.amount(1)
				.item(itemList.get(0))
				.build();

		OrderDetail orderDetail1 = OrderDetail.builder()
				.order(Order.builder().id(1L).build())
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

		OrderDetail orderDetail2 = OrderDetail.builder()
				.order(Order.builder().id(1L).build())
				.price(itemList.get(0).getPrice())
				.amount(1)
				.item(itemList.get(0))
				.build();

		OrderDetail orderDetail3 = OrderDetail.builder()
				.order(Order.builder().id(1L).build())
				.price(itemList.get(1).getPrice())
				.amount(1)
				.item(itemList.get(1))
				.build();

		List<OrderDetail> orderDetailList1 = new ArrayList<>();
		orderDetailList1.add(orderDetail2);
		orderDetailList1.add(orderDetail3);

		Order order1 = Order.builder()
				.shop(shop)
				.price(price)
				.totalQuantity(2)
				.orderDetail(orderDetailList1)
				.build();


		try {
			orderService.save(order);
			orderService.save(order1);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
