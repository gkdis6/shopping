package com.study.orders;

import lombok.Data;

@Data
public class JoinDTO {
	int orderno;
	int contentsno;
	int cartno;
	String id;
	int quantity;
	int total;
	String payment;
	String mname;
	String pname;
}
