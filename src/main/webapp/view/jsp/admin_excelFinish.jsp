<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="col-sm-10 col-md-8 col-lg-6 m-auto">
	<div class="card">
		<div class="text-center">
			<h2 class="mt-5">Excel匯入商品</h2>
	        <h3 class="mt-3">${info}</h3>
	        <div class="mb-5"></div>
	        <a href="${pageContext.request.contextPath}/admin/goProductList.do">
				<button class="btn btn-block productBtn">查看商品列表</button>
			</a>
			<div class="mb-5"></div>
		</div>
	</div>
</div>