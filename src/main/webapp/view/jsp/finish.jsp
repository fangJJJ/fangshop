<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="col-sm-10 col-md-8 col-lg-6 m-auto">
	<div class="card">
		<div class="text-center">
			<h2 class="mt-5">訂單成功送出!</h2>
	        <h3 class="mt-3">感謝訂購</h3>
	        <p class="mt-3">訂單編號： ${ordid}</p>
	        <span>將儘快安排出貨，訂單明細可以至<a href="${pageContext.request.contextPath}/order/goOrderListPage.do">Order</a>查看。</span>
	        <div class="mb-5"></div>
		</div>
	</div>
</div>