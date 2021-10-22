<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="util" uri="/ELFunctions" %>
 
<!DOCTYPE html> 
<html> 
<head>
  <title>장바구니</title>
  <meta charset="utf-8">
 
</head>
<body>
<div class="container">
 <c:set var="sum" value= "0"/>
  <h2>장바구니</h2>
  <table class="table table-striped">
   <thead>
    <tr>
    <th>번호</th>
    <th>상품이미지</th>
    <th>상품명</th>
    <th>가격</th>
    <th>갯수</th>
    <th>총합</th>
    <th>삭제</th>
    </tr>
   </thead>
   <tbody>
 
<c:choose>   
<c:when test="${empty cart}">
   <tr><td colspan="6">등록된 상품이 없습니다.</td>
</c:when>
<c:otherwise>
  
   <c:forEach var="dto" items="${cart}"> 
   
   <tr>
    <td>${dto.contentsno}</td>
    <td>
    <img src="/pstorage/${dto.filename}"  class="img-rounded" width="100px" height="100px">
    </td>
    <td>
    <a href="javascript:read('${dto.contentsno}')">${dto.pname}</a>
    
    </td>
    <td>${dto.price}</td>
    <td>${dto.quantity }</td>
    <td><span id="total">${dto.total }</span></td>
    <td> 
        <a href="./cart/delete/${dto.orderno }">
          <span class="glyphicon glyphicon-trash"></span>
        </a> 
    </td>
   </tr>
   <c:set var="sum" value= "${sum + dto.total }"/>
   </c:forEach>
   </c:otherwise>
   </c:choose>
 
   </tbody>
  
  </table>
  <h2>총합 : <c:out value="${sum }"/></h2>
  <button class="btn btn-default" onclick="location.href='/orderAll'">구매하기</button>
 <button class="btn btn-default" onclick="location.href='/deleteAll'">모두 삭제</button>
</div>
</body> 
</html> 
