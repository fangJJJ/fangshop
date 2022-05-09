<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="row" id="vueCheckOutArea">
	<div class="col-12">
		<div class="d-flex justify-content-center align-items-center">
        	<h5 class="fw-bold mb-0">購物清單</h5>
        </div>
        <div class="text-center text-danger mt-3">
			<span id="errMsg">${MESSAGE_ERROR}</span>
		</div>
		<hr class="my-4">
		
		<div v-for="shoppingProduct in shoppingProductList">
	        <div class="row mb-4 d-flex justify-content-center align-items-center">
	        	<div class="col-md-2 col-lg-2 col-xl-2">
	            	<img v-bind:src="shoppingProduct.picurl" class="img-fluid" />
	            </div>
	            <div class="col-md-3 col-lg-3 col-xl-3 text-center">
	                <h6 class="text-black mb-0">{{shoppingProduct.name}}</h6>
	            </div>
	            <div class="col-md-3 col-lg-3 col-xl-2 text-center">
					<h6 class="mb-0">{{shoppingProduct.qty}}</h6>
				</div>
	            <div class="col-md-3 col-lg-3 col-xl-2 text-center">
	            	<h6 class="mb-0">NT$ {{shoppingProduct.price}}</h6>
	            </div>
			</div>
	        <hr class="my-4">	
		</div>
		
		<div class="justify-content-between align-items-center mb-5 text-end" style="margin-right:10%">
        	<h6 class="mb-0 totalAmount">TOTAL:&nbsp;&nbsp;&nbsp;&nbsp;NT$ {{totalAmount}}</h6>
        </div>
        
        <div class="row">
        	<div class="col-md-6 col-lg-6 offset-md-1">
        		<h5 class="fw-bold mb-0 text-center">收件資料</h5>
        		<form id="checkOutForm" method="post" action="${pageContext.request.contextPath}/cart/saveOrder.do">
        			<label class="form-label" for="typeName">Name</label>
        			<input type="text" id="typeName" class="form-control form-control-lg" name="receiverName"
                      v-model="receiverName" v-bind:value="receiverName"/>
                    <label class="form-label" for="typePhone">Phone</label>
                    <input type="text" id="typePhone" class="form-control form-control-lg" name="receiverPhone"
                      v-model="receiverPhone" v-bind:value="receiverPhone"/>
                    <label class="form-label" for="typeAddress">Address</label>
                    <input type="text" id="typeAddress" class="form-control form-control-lg" name="receiverAddress"
                      v-model="receiverAddress" v-bind:value="receiverAddress"/>
                    <input type="hidden" name="token" value="${token}"/>
        		</form>
        	</div>
        	<div class="col-md-4 col-lg-4 offset-md-1">
	        	<div style="margin-top:30%;">
		        	<button class="btn btn-block checkOutBtn topDistance" v-on:click="backToCartPage()">回購物車頁</button>
		        	<button class="btn btn-block checkOutBtn" v-on:click="saveOrder()">確認購買</button>
	        	</div>
        	</div>
        </div>
		
	</div>
</div>

<script>
new Vue({
	el: "#vueCheckOutArea",
	data: {
		shoppingProductList: [],
		totalAmount: 0,
		receiverName: '',
		receiverPhone: '',
		receiverAddress: ''
	},
	mounted(){
		this.showCheckOut();
	},
	methods:{
		showCheckOut(){
			axios.post('${pageContext.request.contextPath}/cart/showCheckOut.do')
	 		.then(response => {
	 			this.shoppingProductList = response.data.shoppingProductList;
	 			this.totalAmount = response.data.totalAmount;
	 			if(response.data.totalQty){
	 				$('#cartQty').empty().append('('+response.data.totalQty+')');
	 			}else{
	 				$('#cartQty').empty();
	 			}
	 			this.receiverName = response.data.receiverName;
	 			this.receiverPhone = response.data.receiverPhone;
	 			this.receiverAddress = response.data.receiverAddress;
	 	  	})
	 	  	.catch(function (error) {
	 	    	console.log(error);
	 	  	});
		},
		backToCartPage(){
			window.location.href='${pageContext.request.contextPath}/cart/goCartPage.do';
		},
		saveOrder(){
			let receiverName = $('#typeName').val();
			let receiverPhone = $('#typePhone').val();
			let receiverAddress = $('#typeAddress').val();
			if(receiverName == null || receiverName.length == 0){
				alert('收件者姓名不能為空！');
				return;
			}
			if(receiverPhone == null || receiverPhone.length == 0){
				alert('收件者手機不能為空！');
				return;
			}
			if(receiverPhone.length != 10){
				alert('收件者手機應為10碼數字！');
				return;
			}
			if(receiverAddress == null || receiverAddress.length == 0){
				alert('收件者地址不能為空！');
				return;
			}
			$('#checkOutForm').submit();
		}
	}
});
</script>