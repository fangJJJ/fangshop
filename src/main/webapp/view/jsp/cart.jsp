<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<div class="row" id="vueCartArea">
	<div class="col-12">
		<div class="d-flex justify-content-between align-items-center mb-5">
        	<h2 class="fw-bold mb-0">Shopping Cart</h2>
        </div>
        <hr class="my-4">
		
		<div v-if="!shoppingProductList || !shoppingProductList.length">
			<div v-if="isCartLoaded">
				<div class="d-flex justify-content-center align-items-center mb-5">
		        	<h3 class="fw-bold mb-5 pe-3 text-muted">購物車內無商品</h3>
		        </div>
		    </div>
		</div>
		<div v-else>
			<div v-for="shoppingProduct in shoppingProductList">
		        <div class="row mb-4 d-flex justify-content-center align-items-center">
		        	<div class="col-md-2 col-lg-2 col-xl-2">
		            	<img v-bind:src="shoppingProduct.picurl" class="img-fluid" />
		            </div>
		            <div class="col-md-3 col-lg-3 col-xl-3 text-center">
		                <h6 class="text-black mb-0">{{shoppingProduct.name}}</h6>
		            </div>
		            <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
						<button class="btn btn-link px-2"
	                		v-on:click="changeQty(shoppingProduct.prodid, 'less')">
		                    <i class="fas fa-minus"></i>
		                </button>
		
		                <input id="form1" min="1" v-bind:id="shoppingProduct.prodid" v-bind:value="shoppingProduct.qty" type="number"
		                	class="form-control form-control-sm" readonly />
		
		                <button class="btn btn-link px-2"
		                	v-on:click="changeQty(shoppingProduct.prodid, 'add')">
		                    <i class="fas fa-plus"></i>
		                </button>
					</div>
		            <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
		            	<h6 class="mb-0">NT$ {{shoppingProduct.price}}</h6>
		            </div>
		            <div class="col-md-1 col-lg-1 col-xl-1 text-end">
		            	<a href="#!" v-on:click="deleteProduct(shoppingProduct.prodid)" class="text-muted"><i class="fas fa-times"></i></a>
		            </div>
				</div>
		        <hr class="my-4">	
			</div>
			
			<div class="justify-content-between align-items-center mb-5 text-end">
	        	<h4 class="mb-0 totalAmount">TOTAL:&nbsp;&nbsp;&nbsp;&nbsp;NT$ {{totalAmount}}</h4>
	        	<button class="btn btn-block cartBtn" v-on:click="goCheckOut()">結帳</button>
	        </div>	
		</div>
	</div>
</div>



<script>

const carVue = new Vue({
	el: "#vueCartArea",
	data: {
		shoppingProductList: [],
		totalAmount: 0,
		isCartLoaded: false
	},
	mounted(){
		this.showCart();
	},
	methods:{
		showCart(){
			axios.post('${pageContext.request.contextPath}/cart/showCart.do')
	 		.then(response => {
	 			this.shoppingProductList = response.data.shoppingProductList;
	 			this.totalAmount = response.data.totalAmount;
	 			if(response.data.totalQty){
	 				$('#cartQty').empty().append('('+response.data.totalQty+')');
	 			}else{
	 				$('#cartQty').empty();
	 			}
	 			this.isCartLoaded = true;
	 	  	})
	 	  	.catch(function (error) {
	 	    	console.log(error);
	 	  	});
		},
		changeQty(prodid, type){
			let selectNumber = document.getElementById(prodid);
			let originalQty = selectNumber.value;
			switch (type) {
				case 'add':
					selectNumber.stepUp();
					break;
				case 'less':
					selectNumber.stepDown();
					break;
			}
			let newQty = selectNumber.value;
			axios.post('${pageContext.request.contextPath}/cart/changeShoppingProductQty.do?prodid='+prodid+'&qty='+newQty)
	 		.then(response => {
	 			if(response.data.error){
	 				alert(response.data.error);
	 				selectNumber.value = originalQty;
	 				return;
	 			}else{
	 				this.shoppingProductList = response.data.shoppingProductList;
	 				this.totalAmount = response.data.totalAmount;
	 				if(response.data.totalQty){
	 					$('#cartQty').empty().append('('+response.data.totalQty+')');
	 				}else{
	 					$('#cartQty').empty();
	 				}
	 			}
	 	  	})
	 	  	.catch(function (error) {
	 	    	console.log(error);
	 	    	alert("變更商品數量失敗");
	 	    	return;
	 	  	});
		},
		deleteProduct(prodid){
			axios.post('${pageContext.request.contextPath}/cart/deleteShoppingProduct.do?prodid='+prodid)
	 		.then(response => {
	 			if(response.data.error){
	 				alert(response.data.error);
	 				return;
	 			}else{
	 				this.shoppingProductList = response.data.shoppingProductList;
	 				this.totalAmount = response.data.totalAmount;
	 				if(response.data.totalQty){
	 					$('#cartQty').empty().append('('+response.data.totalQty+')');
	 				}else{
	 					$('#cartQty').empty();
	 				}
	 			}
	 	  	})
	 	  	.catch(function (error) {
	 	    	console.log(error);
	 	    	alert("刪除商品失敗");
	 	    	return;
	 	  	});
		},
		goCheckOut(){
			window.location.href='${pageContext.request.contextPath}/cart/goCheckOutPage.do';
		}
	}
});

</script>