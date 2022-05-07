<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">
    <div class="products-header d-flex align-items-center justify-content-between col-12 row">
      	<div class="products-nav col-7">
        	<h4>Excel商品資料</h4>
      	</div>
	</div>
</div>
<br />
<div class="row">
	<table class="table table-hover table-bordered" style="word-break: break-all;">
		<thead>
	        <tr>
		        <th>商品名稱</th>
		        <th>價格</th>
		        <th>描述</th>
		        <th>上架開始時間</th>
		        <th>上架結束時間</th>
		        <th>庫存</th>
		        <th>類別</th>
		        <th>商品圖片連結</th>
	        </tr>
      	</thead>
		<tbody>
			<c:forEach var="batchProduct" items="${batchProductList}">
				<tr class="align-middle">
					<td width="13%">
						${batchProduct.name}
						<p style="color:red">${batchProduct.errorMap['name']}</p>
					</td>
					<td width="5%">
						${batchProduct.price}
						<p style="color:red">${batchProduct.errorMap['price']}</p>
					</td>
					<td width="25%">
						${batchProduct.description}
						<p style="color:red">${batchProduct.errorMap['description']}</p>
					</td>
					<td width="12%">
						${batchProduct.begindate}
						<p style="color:red">${batchProduct.errorMap['begindate']}</p>
					</td>
					<td width="12%">
						${batchProduct.enddate}
						<p style="color:red">${batchProduct.errorMap['enddate']}</p>
					</td>
					<td width="5%">
						${batchProduct.stock}
						<p style="color:red">${batchProduct.errorMap['stock']}</p>
					</td>
					<td width="4%">
						${batchProduct.categoryid}
						<p style="color:red">${batchProduct.errorMap['categoryid']}</p>
					</td>
					<td width="24%">
						${batchProduct.picurl}
						<p style="color:red">${batchProduct.errorMap['picurl']}</p>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="row">
	<div class="col-6 mt-3">
		<c:choose> 
			<c:when test="${hasError}">  
				<a href="/shop/admin/goUploadExcelPage.do">
					<button class="btn btn-block productBtn">重新上傳檔案</button>
				</a>
			</c:when> 
			<c:otherwise>  
				<a href="/shop/admin/saveExcelProduct.do">
					<button class="btn btn-block productBtn">儲存</button>
				</a>
			</c:otherwise>
		</c:choose>
	</div>
</div>
			

