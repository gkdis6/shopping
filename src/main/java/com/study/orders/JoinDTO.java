package com.study.orders;

import lombok.Data;

@Data
public class JoinDTO {
	private int orderno;
	private int contentsno;
	private int cartno;
	private String id;
	private int quantity;
	private int total;
	private String payment;
	private String mname;
	private String pname;
	private String filename;
	private int price;
}
