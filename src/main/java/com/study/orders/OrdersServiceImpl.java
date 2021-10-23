package com.study.orders;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.study.orders.OrdersServiceImpl")
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersMapper mapper;
	
	@Override
	public int addCart(String id) {
		// TODO Auto-generated method stub
		return mapper.addCart(id);
	}

	@Override
	public int order(OrdersDTO dto) {
		// TODO Auto-generated method stub
		return mapper.order(dto);
	}

	@Override
	public int checkCart(String id) {
		// TODO Auto-generated method stub
		return mapper.checkCart(id);
	}

	@Override
	public List<JoinDTO> cartlist(String id) {
		// TODO Auto-generated method stub
		return mapper.cartlist(id);
	}

	@Override
	public int cartOrder(String id) {
		// TODO Auto-generated method stub
		return mapper.cartOrder(id);
	}

	@Override
	public int deleteCart(String id) {
		// TODO Auto-generated method stub
		return mapper.deleteCart(id);
	}

	@Override
	public int delete(int orderno) {
		// TODO Auto-generated method stub
		return mapper.delete(orderno);
	}

	@Override
	public int cart(OrdersDTO dto) {
		// TODO Auto-generated method stub
		return mapper.cart(dto);
	}

	@Override
	public int getCart(String id) {
		// TODO Auto-generated method stub
		return mapper.getCart(id);
	}

	@Override
	public int updateQt(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return mapper.updateQt(map);
	}

}
