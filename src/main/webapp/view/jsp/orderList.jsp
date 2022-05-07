<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<c:when test="${!empty orderList}">
		<c:forEach var="order" items="${orderList}">
			<div class="row mb-5">
				<div class="col-12">
					<table class="table">
						<thead>
					        <tr>
					        	<th scope="col">訂單編號</th>
					          	<th scope="col">購買日期</th>
					          	<th scope="col">結帳金額</th>
					          	<th scope="col">訂單狀態</th>
					        </tr>
					    </thead>
						<tbody>
			        		<tr>
			        			<td width="30%">${order.ordid}</td>
			        			<td width="35%"><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			        			<td width="20%">$ ${order.totalAmount}</td>
			        			<td width="15%">完成</td>
			        		</tr>
			        	</tbody>
					</table>
				</div>
				<div class="order-detail-tile">
				    <button class="btn btn-outline-secondary" type="button" data-bs-toggle="collapse" data-bs-target="#${order.ordid}"
				      aria-expanded="false" aria-controls="${order.ordid}">
				      + 商品明細
				    </button>
			  	</div>
				<div class="col-12 collapse" id="${order.ordid}">
					<table class="table">
						<thead>
					        <tr>
					          	<th scope="col">商品名稱</th>
					          	<th scope="col">價格</th>
					          	<th scope="col">數量</th>
					          	<th scope="col">小計</th>
					        </tr>
					    </thead>
						<tbody>
							<c:forEach var="item" items="${order.items}">
				        		<tr>
				        			<td width="55%">${item.prodName}</td>
				        			<td width="15%">${item.price}</td>
				        			<td width="15%">${item.qty}</td>
				        			<td width="15%">${item.amount}</td>
				        		</tr>
			        		</c:forEach>
			        	</tbody>
					</table>
					<div>
			      		<ul class="list-group">
			        		<li class="list-group-item">收件者姓名: ${order.receiverName}</li>
			        		<li class="list-group-item">收件者地址: ${order.receiverPhone}</li>
			        		<li class="list-group-item">收件者手機: ${order.receiverAddress}</li>
			      		</ul>
			    	</div>
				</div>
			</div>
		</c:forEach>   
	</c:when>
	<c:otherwise>
		<div class="col-12">
			<div class="d-flex justify-content-center align-items-center mb-5">
	        	<h3 class="fw-bold mb-5 pe-3 text-muted">無訂單</h3>
	        </div>
		</div>
	</c:otherwise>
</c:choose>

