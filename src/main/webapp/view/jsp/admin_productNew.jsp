<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 

<div class="col-sm-10 col-md-8 col-lg-8 m-auto">
	<div class="card">
		<form action="/shop/admin/newProduct.do" method="post" onsubmit="return saveNewProduct()">
			<div class="mb-3 mt-3 col-12 text-center">
				<h3>新增商品</h3>
			</div>
			<div class="mb-3 mt-4 col-10 mx-auto">
				<label class="form-label">商品名稱</label> 
				<form:errors path="product.name" cssStyle="color:red"></form:errors>
				<input type="text" name="name" id="name" class="form-control" value="${product.name}" required />
			</div>
			<div class="mb-3 col-10 mx-auto">
				<label class="form-label">商品類別</label>
				<select name="categoryid" style="width:30%;" id="categoryid">
					<c:forEach var="category" items="${categoryList}">
						<option value="${category.categoryid}">${category.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="mb-3 col-10 mx-auto">
				<label class="form-label">價格</label> 
				<form:errors path="product.price" cssStyle="color:red"></form:errors>
				<input type="number" name="price" id="price" class="form-control" value="${product.price}" required />
			</div>
			<div class="mb-3 col-10 mx-auto">
				<label class="form-label">庫存</label> 
				<form:errors path="product.stock" cssStyle="color:red"></form:errors>
				<input type="number" name="stock" id="stock" class="form-control" value="${product.stock}" required />
			</div>
			<div class="row mb-3 justify-content-center">
				<div class="col-10">
					<label class="form-label">上架開始時間</label>
					<form:errors path="product.begin_date" cssStyle="color:red"></form:errors>
				</div>
				<div class="col-5">
					<input type="date" name="begin_date" id="beginDate" class="form-control" value="${product.begin_date}" required />
				</div>
				<div class="col-5">
					<input type="time" name="begin_time" id="beginTime" class="form-control" value="${product.begin_time}" required />
				</div>
			</div>
			<div class="row mb-3 justify-content-center">
				<div class="col-10">
					<label class="form-label">上架結束時間</label>
					<form:errors path="product.end_date" cssStyle="color:red"></form:errors>
				</div>
				<div class="col-5">
					<input type="date" name="end_date" id="endDate" class="form-control" value="${product.end_date}" required />
				</div>
				<div class="col-5">
					<input type="time" name="end_time" id="endTime" class="form-control" value="${product.end_time}" required />
				</div>
			</div>
			<div class="mb-3 col-10 mx-auto">
				<label class="form-label">商品圖片網址</label>
				<form:errors path="product.picurl" cssStyle="color:red"></form:errors> 
				<input type="url" name="picurl" id="picurl" class="form-control" placeholder="https://example.com"
      			pattern="https://.*" value="${product.picurl}" required />
			</div>
			<div class="mb-3 col-10 mx-auto">
				<label class="form-label">商品描述</label>
				<form:errors path="product.description" cssStyle="color:red"></form:errors>
				<br>
				<textarea name="description" id="description" rows="6" cols="50">${product.description}</textarea>
			</div>
			<div class="col text-center">
				<button type="submit" class="btn btn-block col-4 mb-4">送 出</button>
			</div>
		</form>
	</div>
</div>
<script>
$(function() {
	$('#categoryid').val(${product.categoryid});
});
function saveNewProduct(){
	if($('#name').val().length > 50){
		alert('商品名稱長度不能大於50個字');
		$('#name').focus();
		return false;
	}
	if($('#picurl').val().length > 200){
		alert('商品圖片連結長度不能大於200個字');
		$('#picurl').focus();
		return false;
	}
	if($('#description').val().length > 200){
		alert('商品描述長度不能大於200個字');
		$('#description').focus();
		return false;
	}
	if($('#price').val() < 0){
		alert('商品價格不可以小於0');
		$('#price').focus();
		return false;
	}
	if($('#stock').val() < 0){
		alert('庫存不可以小於0');
		$('#stock').focus();
		return false;
	}
	let currentDate = new Date();
	let beginDate = getFormatDate($('#beginDate').val(), $('#beginTime').val());	
	let endDate = getFormatDate($('#endDate').val(), $('#endTime').val());
	if(endDate < currentDate){
		alert('商品上架結束時間必須大於今天');
		$('#endDate').focus();
		return false;
	}
	if(endDate < beginDate){
		alert('商品上架結束時間必須晚於上架開始時間');
		$('#endDate').focus();
		return false;
	}
}
function getFormatDate(date, time){
	let year = date.substring(0, 4);
	let month = date.substring(5, 7);
	let day = date.substring(8, 10);
	let hour = time.substring(0, 2);
	let minute = time.substring(3, 5);
	//JavaScript counts months from 0 to 11:January = 0、December = 11.
	//所以月份必須減1
	return new Date(year, month - 1, day, hour, minute);
}
</script>
