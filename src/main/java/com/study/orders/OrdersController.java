package com.study.orders;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.member.MemberDTO;

@Controller
public class OrdersController {

	@Autowired
	@Qualifier("com.study.orders.OrdersServiceImpl")
	private OrdersService service;

	@GetMapping("/order")
	public String order(HttpSession session, Model model) {

		String id = (String)session.getAttribute("id");
		Integer cartno = (Integer)session.getAttribute("cartno");
		 
		if(id==null) {
			return "redirect:./login";
		}else if(cartno == null){
			cartno = service.addcart(id);
		}
		OrdersDTO dto = service.order(id);
		
		model.addAttribute("dto", dto);
		return "/order";
	}
	
}
