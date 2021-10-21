package com.study.orders;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.study.member.MemberDTO;
import com.study.notice.NoticeDTO;

@Controller
public class OrdersController {

	@Autowired
	@Qualifier("com.study.orders.OrdersServiceImpl")
	private OrdersService service;
	
	@GetMapping("/order")
	public String order(HttpSession session) {
		String id = (String)session.getAttribute("id");
		 
		if(id==null) {
			return "redirect:./member/login";
		}else{
			return "/order";
		}
	}

	@PostMapping("/order")
	public String order(OrdersDTO dto, HttpSession session) {

		String id = (String)session.getAttribute("id");
		dto.setId(id);
		if(id==null) {
			return "redirect:./login";
		}
		if (service.order(dto) > 0) {
			System.out.println("okokokokokokokokokokokokokokokokok");
			return "redirect:/contents/detail/"+dto.getContentsno();
		} else {
			return "/error";
		}

	}
	
}
