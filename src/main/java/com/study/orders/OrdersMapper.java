package com.study.orders;

import java.util.List;
import java.util.Map;

public interface OrdersMapper {

	int addCart(String id);

	int order(OrdersDTO dto);

	int checkCart(String id);

	List<JoinDTO> cartlist(String id);

	int cartOrder(String id);

	int deleteCart(String id);

	int delete(int orderno);

	int cart(OrdersDTO dto);

	int getCart(String id);

	int updateQt(Map<String, Integer> map);


}
