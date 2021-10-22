<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html> 
<html> 
<head>
  <title>주문</title>
  <meta charset="utf-8">
  <style>
  div img{
  float:right;
  }
  </style>
</head>
<body> 

<div class="container">
<h1 class="col-sm-offset-2 col-sm-10">상품 담기</h1>
<form class="form-horizontal" 
      action="/appendCart"
      method="post"
      enctype="multipart/form-data"
      onsubmit="return checkIn(this)">

  <div class="form-group">
    <input type ="hidden" name="contentsno" id="contentsno" class="form-control" value=${dto.contentsno}>
    <input type ="hidden" name="filename" id="filename" class="form-control" value=${dto.filename}>
    <div class="col-sm-6">
  	<img src="${root}/pstorage/${dto.filename}" 
 		width="250px" height="250px" class="img-rounded" >
 	</div>
  </div>
  
  <div class="form-group">
    <label class="control-label col-sm-2" for="pname">상품명</label>
    <div class="col-sm-8">
      <input type ="hidden" name="pname" id="pname" class="form-control" value=${dto.pname}> ${dto.pname }
      
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="total">가격</label>
    <div class="col-sm-8">
      <input type ="hidden" name="total" id="total" class="form-control" value=${dto.price }> ${dto.price }
    </div>
  </div>
  
  <div class="form-group">
    <label class="control-label col-sm-2" for="quantity">갯수</label>
    <div class="col-sm-6">
      <select class="form-control" name="quantity" id="quantity" onchange="cal(this.value);">
        <option value=1 selected>1</option>
        <option value=2>2</option>
        <option value=3>3</option>
      </select>
    </div>
  </div>
  <script>
  var cal=function(value){
	console.log("값변경 : "+ value);
	$("#totall").text(value*${dto.price});
	$("#total").val(value*${dto.price});
	
  }
  </script>
  
  <div class="form-group">
    <label class="control-label col-sm-2" for="total">총합</label>
    <div class="col-sm-6">
      <span id="totall" >${dto.price }</span>
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="payment">결제수단</label>
    <div class="col-sm-6">
      <select class="form-control" name="payment" id="payment">
        <option value="신용카드" selected>신용카드</option>
        <option value="무통장입금">무통장입금</option>
        <option value="간편결제">간편결제</option>
      </select>
    </div>
  </div>
   <div class="form-group">
   <div class="col-sm-offset-2 col-sm-5">
    <button class="btn">등록</button>
    <button type="reset" class="btn">취소</button>
   </div>
 </div>
</form>
</div>
</body> 
</html> 