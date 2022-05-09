<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="loginVue">
<form action="${pageContext.request.contextPath}/member/login.do" method="post">
	<div class="col-sm-10 col-md-8 col-lg-6 m-auto">
		<div class="card card-body d-flex align-items-center">
			<h2 class="text-center mt-3">登 入</h2>
			<div class="mb-3 col-8">
				<span id="errMsg">${MESSAGE_ERROR}</span>
			</div>
			<div class="mb-3 col-8">
				<label class="form-label">Email</label> <input type="email"
					name="email" class="form-control" value="${member.email}">
			</div>
			<div class="mb-3 col-8">
				<label class="form-label">密 碼</label> <input type="password"
					name="password" class="form-control" value="${member.password}">
			</div>
			<button type="submit" class="btn btn-block col-6 mt-4">送 出</button>
			<a href="javascript:facebookLogin();" class="fb btn mt-3"> 
				<i class="fab fa-facebook fa-fw"></i> Login with Facebook
			</a> 
			<span class="lead mt-4 mb-3"> No Account? <a href="${pageContext.request.contextPath}/member/doRegister.do">Register</a>
			</span>
		</div>
	</div>
</form>

<form method="post" id="socialForm" action="${pageContext.request.contextPath}/member/socialLogin.do">
	<input type="hidden" name="socialsour" id="socialsour" value="" /> 
	<input type="hidden" name="token" id="token" value="" />
</form>
</div>

<script>
function facebookLogin(){
	// 檢查登入狀態
	FB.getLoginStatus(function(response) {
	    // 登入狀態 - 已登入
	    if(response.status === "connected"){
	    	 $('#token').val(response.authResponse.accessToken);
    		 $('#socialsour').val('F');
    		 $('#socialForm').submit();
	    }else{
	    	// 進行Facebook登入程序
		     FB.login(function(response) {
		    	 if(response.status === "connected"){
		    		 $('#token').val(response.authResponse.accessToken);
		    		 $('#socialsour').val('F');
		    		 $('#socialForm').submit();
		     	}else{
		     		return;
		     	}
		     }, {
		         scope: 'public_profile,email',
		         auth_type: 'rerequest'
		     });
	    }
    });
}
</script>