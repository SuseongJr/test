<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp" %>

<div class="container">

<c:forEach var="board" items="${boards}">

  <div class="card m-2">
    <div class="w3-container w3-center">
	      <div class="card-body">
	      
	      	<h4 class="card-title">${board.title}</h4>
	      	<a href="#" class="btn btn-primary">${boarad.content}</a>
	      	
	      </div>

    </div>
  </div>
  
</c:forEach>
  
</div>



<%@ include file="layout/footer.jsp" %>