<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="row" id="vueArea">
	<div class="col-3">
		<nav class="nav flex-column categoryNav">
			<a class="nav-link" href="#" id="all" v-on:click="getProductListByCategory(0, 1)">all</a>
			<div v-for="category in categoryList">
		    	<a class="nav-link" href="#" v-on:click="getProductListByCategory(category.categoryid, 1)">{{category.name}}</a>
			</div>
		</nav>
	</div>
	<div class="col-9">
		<div class="row row-cols-3">
			<div v-for="product in allProductList" style="padding:0px">
				<div class="col">
					<div class="product" v-on:click="getProduct(product.prodid)">
						<img v-bind:src="product.picurl" />
						<div class="info">
							<span>{{product.name}}</span>
							<p>NT$ {{product.price}}</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<nav>
			<ul class="pagination justify-content-center">
				 <li class="page-item" v-for="index in pages">
				 	<a class="page-link" href="#" v-on:click="getProductListByCategory(categoryid, index)">{{index}}</a>
				 </li>
			</ul>
		</nav>
	</div>
</div> 


<script>
const vm = new Vue({
	el: "#vueArea",
	data: {
		categoryList: [],
		allProductList: [],
		pages: 0,
		categoryid: 0
	},
	mounted() {
		this.getCategoryList();
		this.getProductListByCategory(0, 1);
	},
	methods:{
		getProductListByCategory: function(categoryid, pageNum) {
			this.categoryid = categoryid;
			axios.post('/shop/product/ajaxProductListByCategoryPage.do?categoryid='+categoryid+'&pageNum='+pageNum)
			.then(response => {
				this.allProductList = response.data.result.list;
		    	this.pages = response.data.result.pages;
		  	})
		  	.catch(function (error) {
		    	console.log(error);
		  	});
		},
		getCategoryList(){
			axios.post('/shop/product/ajaxCategoryList.do')
	 		.then(response => {
	 	    	this.categoryList = response.data.result;
	 	  	})
	 	  	.catch(function (error) {
	 	    	console.log(error);
	 	  	});
		},
		getProduct: function(prodid) {
			window.location.href='/shop/product/getProduct.do?prodid='+prodid;
		}
	}
});


</script>