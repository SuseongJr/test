<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp" %>

<div class="container">

<c:forEach var="board" items="${boards.content}">

  <div class="card m-2">
    <div class="w3-container w3-center">
	      <div class="card-body">
	      
	      	<h4 class="card-title">${board.title}</h4>
	      	<a href="#" class="btn btn-primary">상세보기</a>
	      	
	      </div>

    </div>
  </div>
  
</c:forEach>

 <!-- 페이징 -->
<ul class="pagination justify-content-center">

<c:if test="${!boards.first}">
	<li class="page-item"><a class="page-link" href="?page=0">First</a></li>
</c:if>

<c:choose>
	<c:when test="${boards.first}">
		<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
	</c:when>
	<c:otherwise>
		<li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
	</c:otherwise>
</c:choose>


<c:forEach var="i" begin="${firstPage}" end="${lastPage}">
    <c:choose>
        <c:when test="${i eq boards.number+1}">
            <li class="page-item active"><a class="page-link" href="?page=${i -1}">${i}</a></li>
        </c:when>
        <c:otherwise>
            <li class="page-item"><a class="page-link" href="?page=${i -1}">${i}</a></li>
        </c:otherwise>
    </c:choose>
</c:forEach>



<c:choose>
	<c:when test="${boards.last}">
		<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
	</c:when>
	<c:otherwise>
		<li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
	</c:otherwise>
</c:choose>

<c:if test="${!boards.last}">
	<li class="page-item"><a class="page-link" href="?page=${boards.totalPages-1}">Last</a></li>
</c:if>

</ul>

<div class="pagination justify-content-center">${currentPage} / ${totalPage} Page</div>
  
</div>



<%@ include file="layout/footer.jsp" %>