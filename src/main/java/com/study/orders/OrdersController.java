package com.study.orders;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
			return "redirect:/contents/detail/"+dto.getContentsno();
		} else {
			return "/error";
		}

	}
	
	@GetMapping("/cartlist")
	public String cartlist(HttpSession session, Model model) {
		  String id = (String)session.getAttribute("id");
		 
		  if(id==null) {
		       return "redirect:./login";
		  }else {
		  
		       List<JoinDTO> cart = service.cartlist(id);
		      
		       model.addAttribute("cart", cart);
		      
		   return "/cartlist";
		  }
	}
	
	@PostMapping(value = "/cartlist", produces = "application/json")
	@ResponseBody
	public Map<String, Integer> modify(@RequestBody OrdersDTO dto, HttpServletRequest request) throws IOException {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("orderno", dto.getOrderno());
		map.put("quantity", dto.getQuantity());
		map.put("total", dto.getTotal());
		service.updateQt(map);
		
		return map;
	}
	
	@GetMapping("/orderAll")
	public String orderAll(HttpSession session) {

		String id = (String)session.getAttribute("id");
		if(id==null) {
			return "redirect:./login";
		}
		if (service.cartOrder(id) > 0) {
			return "/cartlist";
		} else {
			return "/error";
		}

	}
	
	@GetMapping("/deleteAll")
	public String deleteAll(HttpSession session) {

		String id = (String)session.getAttribute("id");
		if(id==null) {
			return "redirect:./login";
		}
		if (service.deleteCart(id) > 0) {
			return "/cartlist";
		} else {
			return "/error";
		}

	}
	
	@GetMapping("/cart/delete/{orderno}")
	public String delete(HttpServletRequest request, @PathVariable("orderno") int orderno) throws IOException {

		if (service.delete(orderno) > 0) {
			return "redirect:/cartlist";
		} else {
			return "./error";
		}
	}
	
	@GetMapping("/appendCart")
	public String cart(HttpSession session) {
		String id = (String)session.getAttribute("id");
		 
		if(id==null) {
			return "redirect:./member/login";
		}else{
			return "/appendCart";
		}
	}

	@PostMapping("/appendCart")
	public String cart(OrdersDTO dto, HttpSession session) {

		String id = (String)session.getAttribute("id");
		dto.setId(id);
		if(id==null) {
			return "redirect:./login";
		}
		if(service.checkCart(id)<1) {
			service.addCart(id);
		}
		dto.setCartno(service.getCart(id));
		if (service.cart(dto) > 0) {
			return "redirect:/contents/detail/"+dto.getContentsno();
		} else {
			return "/error";
		}

	}
}
