<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html> 
<html> 
<head>
  <title>삭제</title>
  <meta charset="utf-8">
  <style>
   #red{
    color:red;
   }
  </style>
</head>
<body> 
<div class="container">

<h1 class="col-sm-offset-2 col-sm-10">삭제</h1>
<form class="form-horizontal" 
      action="/contents/delete"
      method="post"
      >
 <input type="hidden" name="contentsno" value="${contentsno}">
  
  <p id='red' class="col-sm-offset-2 col-sm-6">삭제하면 복구할 수 없습니다.</p>
  
   <div class="form-group">
   <div class="col-sm-offset-2 col-sm-5">
    <button class="btn">삭제</button>
    <button class="btn" onclick="history.back()">취소</button>
   </div>
 </div>
</form>
</div>
</body> 
</html> 
