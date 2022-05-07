<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.fang.util.CommonUtil"%>

<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<header class="container-fluid shadow-sm">
	<nav class="navbar navbar-expand-lg navbar-light">
		<a class="navbar-brand" href="/shop/Index.do">
			Fang<span>Shop</span>.
		</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
            aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
		<div class="collapse navbar-collapse" id="vueTemplate">
			<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link" aria-current="page" href="/shop/Index.do">HOME</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/shop/product/productList.do">PRODUCT</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/shop/cart/goCartPage.do">
						CART 
						<span id="cartQty"> 
						<%if(CommonUtil.getCartSession(session) != null){ %>
							<%="("+CommonUtil.getCartSession(session).getTotalQty()+")" %>
						<%}%>
						</span>
					</a>
				</li>
				<%if(CommonUtil.getCurrentMember(session) == null){ %>
				<li class="nav-item">
					<a class="nav-link" href="/shop/member/doLogin.do">LOGIN</a>
				</li>
				<%}else{%>
				<li class="nav-item">
					<a class="nav-link" href="/shop/order/goOrderListPage.do">ORDER</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/shop/member/logout.do">LOGOUT</a>
				</li>
				<%}%>
			</ul>
		</div>
	</nav>
</header>

