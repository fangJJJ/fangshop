<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-md-6 productPic">
		<img src="${product.picurl}" />
	</div>
	<div class="col-md-6">
		<h3>${product.name}</h3>
		<div class="description">${product.description}</div>
		<div id="vueProductArea">
			<div class="price">NT$ ${product.price}</div>
			<div class="qty">
				數量：<input type="number" min="1" max="${product.stock}" name="qty" v-model.number="qty" />
			</div>
			<br>
			<div v-if="${product.stock > 0}">
				<button class="btn btn-block productBtn" v-on:click="addCart()">加入購物車</button>
			</div>
			<div v-else>
				<button class="btn btn-block productDisabledBtn" disabled>已售完</button>
			</div>
		</div>
	</div>
</div>

<script>
const vm = new Vue({
	el: "#vueProductArea",
	data: {
		prodid: ${product.prodid},
		qty: 1
	},
	methods:{
		addCart(){
			if(this.qty == null || this.qty < 1){
				alert("購買數量必須大於1");
				return;
			}
			axios.post('/shop/cart/ajaxAddCart.do?prodid='+this.prodid+'&qty='+this.qty)
	 		.then(response => {
	 	    	if(response.data.error){
	 	    		alert(response.data.error);
	 	    	}else{
	 	    		$('#cartQty').empty().append('('+response.data.totalQty+')');
	 	    	}
	 	  	})
	 	  	.catch(function (error) {
	 	    	console.log(error);
	 	    	alert("加入購物車失敗");
	 	    	return;
	 	  	});
		}
	}
});
</script>