package com.study.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.study.member.MemberService;

@Controller
public class OrdersController {

	@Autowired
	@Qualifier("com.study.orders.OrdersServiceImpl")
	private OrdersService service;
}
