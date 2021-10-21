package com.study.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.study.orders.OrdersServiceImpl")
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersMapper mapper;
	
	@Override
	public int addcart(String id, int contentsno) {
		// TODO Auto-generated method stub
		return mapper.addcart(id, contentsno);
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

}
