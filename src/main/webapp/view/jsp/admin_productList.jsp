<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">
    <div class="products-header d-flex align-items-center justify-content-between col-12 row">
      	<div class="products-nav col-7">
        	<a href="${pageContext.request.contextPath}/admin/goNewProductPage.do">
		    	<button type="button" class="btn btn-success">新增商品</button>
        	</a>
        	<a href="${pageContext.request.contextPath}/admin/goUploadExcelPage.do">
		    	<button type="button" class="btn btn-success">Excel匯入商品</button>
        	</a>
      	</div>
	</div>
</div>
<br />
<div class="row">
	<table class="table table-hover table-bordered">
		<thead>
	        <tr>
	          	<th scope="col">商品編號</th>
		        <th scope="col">商品名稱</th>
		        <th scope="col">上架開始時間</th>
		        <th scope="col">上架結束時間</th>
		        <th scope="col">編輯商品</th>
	        </tr>
      	</thead>
		<tbody>
			<c:forEach var="product" items="${productList}">
				<tr class="align-middle">
					<td width="10%"><a href="${pageContext.request.contextPath}/admin/goProductPage.do?prodid=${product.prodid}">${product.prodid}</a></td>
					<td width="30%">${product.name}</td>
					<td width="20%"><fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm" value="${product.begindate}" /></td>
					<td width="20%"><fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm" value="${product.enddate}" /></td>
					<td width="20%">
						<button class="btn btn-block productBtn" onclick="editProduct(${product.prodid})">編輯</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<nav class="mt-5">
	<ul class="pagination justify-content-center">
		<c:forEach var="index"	begin="1" end="${pages}">
		 	<li class="page-item">
		 		<a class="page-link" href="#" onclick="getProductListByPage(${index})">${index}</a>
		 	</li>
		</c:forEach>
	</ul>
</nav>

<script>
function editProduct(prodid){
	window.location.href='${pageContext.request.contextPath}/admin/goEditProductPage.do?prodid='+prodid;
}

function getProductListByPage(pageNum){
	window.location.href='${pageContext.request.contextPath}/admin/goProductList.do?pageNum='+pageNum;
}
</script>
