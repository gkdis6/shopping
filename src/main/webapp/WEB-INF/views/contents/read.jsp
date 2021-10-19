<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="util" uri="/ELFunctions" %>
<c:set var="root" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
  <title>상품</title>
  <meta charset="utf-8">
  <script type="text/javascript">
  function updateM(){
	  var url = "/contents/update";
 	  url += "/${dto.contentsno}";
	  
	  location.href = url;
  }
  
  function deleteM(){
	  var url = "/contents/delete";
	  url += "/${dto.contentsno}";
	  location.href = url;
  }
  </script>
</head>
<body>
<div class="container">

<h2 class="col-sm-offset-2 col-sm-10">${dto.pname}의 정보</h2>
 <table class="table table-bordered">
 <tr>
 	<td colspan="2" style="text-align: center">
 		<img src="${root}/pstorage/${dto.filename}" 
 		width="250px" height="250px" class="img-rounded" >
 	</td>
 </tr>
 <tr>
 	<th>번호</th>
 	<td>${dto.contentsno}</td>
 </tr>
 <tr>
 	<th>카테고리</th>
 	<td>${dto.cateno}</td>
 </tr>
 <tr>
 	<th>이름</th>
 	<td>${dto.pname}</td>
 </tr>
 <tr>
 	<th>가격</th>
 	<td>${dto.price}</td>
 </tr>
 <tr>
 	<th>재고</th>
 	<td>${dto.stock}</td>
 </tr>
 <tr>
 	<th>상세정보</th>
 	<td>${dto.detail}</td>
 </tr>
 
 </table>
 <div style="text-align: center">
 <button class="btn btn-default" onclick="updateM()">정보수정</button>
 <button class="btn btn-default" onclick="deleteM()">상품삭제</button>
 <button class="btn btn-default" onclick="location.href='${root}/contents/list'">상품목록</button>
 </div>
 
 <br>
 
</div>
</body>
</html>