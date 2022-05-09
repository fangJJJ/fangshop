<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="row">
	<div class="mb-3 mt-3 col-12 text-center">
		<h3>Excel匯入商品</h3>
	</div>
	<div class="mb-3 col-10">
		<span id="errMsg">${MESSAGE_ERROR}</span>
 	</div>
	<div class="d-flex justify-content-center col-12">
		<form method="post" id="uploadForm" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/uploadExcel.do"
			onsubmit="return uploadExcel()">
		    <input type="file" name="file" id="formfile" accept=".xls,.xlsx" /> 
		    <div style="text-align:center;">
		    	<input type="submit" class="btn btn-block productBtn mt-3" value="上傳" />
		    </div>
		</form>
	</div>
	<br>
	<div class="d-flex justify-content-center col-12 mt-3">
		<a href="${pageContext.request.contextPath}/admin/downloadExcel.do">
			<button class="btn btn-success" style="padding:6px 25px;">下載Excel範本</button>
		</a>
	</div>
</div>

<script>
function uploadExcel(){
	if($('#formfile').get(0).files.length == 0){
		alert('請上傳excel檔案');
		return false;
	}
}
</script>