package com.study.orders;

public interface OrdersMapper {

	Integer addcart(String id, int contentsno);

	int order(OrdersDTO dto);

	int checkCart(String id);


}
