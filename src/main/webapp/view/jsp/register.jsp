<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form action="/shop/member/register.do" method="post">
	<div class="col-sm-10 col-md-8 col-lg-6 m-auto" >
		<div class="card card-body d-flex align-items-center">
			<h2 class="text-center mt-3">加入會員</h2>
			<div class="mb-3 col-8">
				<span id="errMsg">${MESSAGE_ERROR}</span>
			</div>
			<div class="mb-3 col-8">
				<label class="form-label">姓 名</label> 
				<input type="text" name="name" class="form-control" value="${member.name}" >
			</div>
			<div class="mb-3 col-8">
				<label class="form-label">Email</label> 
				<input type="email" name="email" class="form-control" value="${member.email}" >
			</div>
			<div class="mb-3 col-8">
				<label class="form-label">密 碼</label>
				<input type="password" name="password" class="form-control" value="${member.password}" >
			</div>
			<div class="mb-3 col-8">
				<label class="form-label">手 機</label> 
				<input type="text" name="mobile" class="form-control" value="${member.mobile}" >
			</div>
			<button type="submit" class="btn btn-block col-6 mt-4">送 出</button>
			<span class="lead mt-4 mb-3">
				Have An Account?
				<a href="/shop/member/doLogin.do">Login</a>
			</span>
		</div>
	</div>
</form>
