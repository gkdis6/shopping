package com.study.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrdersController {

	@Autowired
	@Qualifier("com.study.orders.OrdersServiceImpl")
	private OrdersService service;

	@GetMapping("/order")
	public String order() {

		return "/contents/mainlist";
	}
	
}
