<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
<c:choose>
	<c:when test="${!empty product}">
		<div class="col-sm-10 col-md-8 col-lg-8 m-auto">
			<div class="card">
				<div class="mb-3 mt-3 col-12 text-center">
					<h3>商品檢視</h3>
				</div>
				<div class="row mb-3 mt-4 col-10 mx-auto">
					<div class="col-3">商品名稱 :</div> 
					<div class="col-7">${product.name}</div>
				</div>
				<div class="row mb-3 col-10 mx-auto">
					<div class="col-3">商品類別 :</div>
					<div class="col-7">
						<select name="categoryid" style="width:30%;" id="categoryid" disabled>
							<c:forEach var="category" items="${categoryList}">
								<option value="${category.categoryid}">${category.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row mb-3 col-10 mx-auto">
					<div class="col-3">價格 :</div> 
					<div class="col-7">${product.price}</div>
				</div>
				<div class="row mb-3 col-10 mx-auto">
					<div class="col-3">庫存 :</div> 
					<div class="col-7">${product.stock}</div> 
				</div>
				<div class="row mb-3 col-10 mx-auto">
					<div class="col-3">
						<label class="form-label">上架開始時間 :</label>
					</div>
					<div class="col-7">
						<fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm" value="${product.begindate}" />
					</div>
				</div>
				<div class="row mb-3 col-10 mx-auto">
					<div class="col-3">
						<label class="form-label">上架結束時間 :</label>
					</div>
					<div class="col-7">
						<fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm" value="${product.enddate}" />
					</div>
				</div>
				<div class="row mb-3 col-10 mx-auto">
					<label class="form-label">商品圖片網址 :</label>
					<span>${product.picurl}</span>
				</div>
				<div class="row mb-3 col-10 mx-auto">
					<label class="form-label">商品描述 :</label>
					<br>
					<textarea name="description" rows="6" cols="40" readonly>${product.description}</textarea>
				</div>
				<div class="col text-center">
					<a class="btn btn-block productBtn col-4 mb-4" href="/shop/admin/goProductList.do">回商品列表頁</a>
				</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="col-12">
			<div class="d-flex justify-content-center align-items-center mb-5">
	        	<h3 class="fw-bold mb-5 pe-3 text-muted">無此商品，請重新操作</h3>
	        </div>
		</div>
	</c:otherwise>
</c:choose>
	
<script>
$(function() {
	$('#categoryid').val(${product.categoryid});
});
</script>