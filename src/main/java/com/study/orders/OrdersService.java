package com.study.orders;

public interface OrdersService {

	int addcart(String id, int contentsno);

	int order(OrdersDTO dto);

	int checkCart(String id);

}
