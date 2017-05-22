<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<script>


function synccreateOrderButton(){
	var selectAny=false;
	$(".cartProductItemIfSelected").each(function(){
		if("selectit"==$(this).attr("selectit"))
			selectAny = true;
	});
	if(selectAny){
		$("button.createOrderButton").css("background-color","#C40000");
		$("button.createOrderButton").removeAttr("disabled");
	}
	else{
		$("button.createOrderButton").css("background-color","#AAAAAA")
		$("button.createOrderButton").attr("disabled","disabled");
	}
}

function syncSelect(){
	var selectAll=true;
	$(".cartProductItemIfSelected").each(function(){
		if("false"==$(this).attr("selectit")){
			selectAll = false;
		}
	});
	if(selectAll){
		$("img.selectAllItem").attr("src","img/site/cartSelected.png");
	}
	else{
		$("img.selectAllItem").attr("src"."img/site/cartNotSelected.png");
	}
}

function calcCartSumPriceAndNumber(){
	var sum = 0;
	var totalNumber=0;
	$("img.cartProductItemIfSelected[selectit='selectit']").each(function(){
		var oiid = $(this).attr("oiid");
		var price = $(".cartProductItemSmallSumPrice[oiid="+oiid+"]").text();
		price = price.replace(/,/g, "");
		price = price.replace(/￥/g, "");
		sum += new Number(price);

		var num = $(".orderItemNumberSetting[oiid="+oiid+"]").val();
		totalNumber += new Number(num);
	});

	$("span.cartSumPrice").html("￥"+formatMoney(sum));
	$("span.cartTitlePrice").html("￥"+formatMoney(sum));
	$("span.cartSumNumber").html(totalNumber);
}

</script>

<title>购物车</title>
<div class="cartDiv">
	<div class="cartTitle pull-right">
		<span>已选商品（不含运费）</span>
		<span class="cartTitlePrice"> 0.00</span>
		<button class="createOrderButton" disabled="disabled">结 算</button>
	</div>

	<div class="carProductList">
		<table class="cartProductTable">
			<thead>
				<tr>
					<th class="selectedAndImage">
						<img selectit="false" class="selectAllItem" src="img/site/cartNotSelected.png">
						全选
					</th>
					<th>商品信息</th>
					<th>单价</th>
					<th>数量</th>
					<th width="120px">金额</th>
					<th class="operation">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ois}" var="oi">
					<tr oiid=${oi.id} class="cartProductItemTR">	
						<td>
							<img selectit="false" oiid="${oi.id}" class="cartProductItemIfSelected" src="img/site/cartNotSelected.png">
							<a style="display:none;" href="#nowhere"><img src="img/site/cartSelected.png"></a>
							<img class="cartProductImg" src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg">
						</td>
						<td>
							<div class="cartProductLinkOutDiv">
								<a href="foreproduct?pid=${oi.product.id}" class="cartProductLink">${oi.product.name}</a>
								<div class="cartProductLinkInnerDiv">
									<img src="img/site/creditcart.png" title="支持信用卡支付">
									<img src="img/site/7day.png" title="消费者保障服务，卖家承诺7天退换">
									<img src="img/site/promise.png" title="消费者保障服务，卖家承诺如实描述">
								</div>
							</div>
						</td>
						<td>
							<span class="cartProductItemOringalPrice">￥${oi.product.orignalPrice}</span>
							<span  class="cartProductItemPromotionPrice">￥${oi.product.promotePrice}</span>
						</td>
						<td>
							<div class="cartProductChangeNumberDiv">
								<span class="hidden orderItemStock" pid="${oi.product.id}">${oi.product.stock}</span>
								<span class="hidden orderItemPromotePrice" pid="${oi.product.id}">${oi.product.promotePrice}</span>
								<a href="#nowhere" class="numberMinus" pid="${oi.product.id}">-</a>
								<input pid="${oi.product.id}" oiid="oi.id" class="orderItemNumberSetting" autocomplete="off" value="${oi.number}">
								<a href="#nowhere" stock="oi.product.stock" pid="${oi.product.id}" class="numberPlus">+</a>
							</div>
						</td>
						<td>
							<span class="cartProductItemSmallSumPrice" oiid="${oi.id}" pid="${oi.product.id}">￥<fmt:formatNumber type="number" value="${oi.product.promotePrice*oi.number}" minFractionDigits="2"/></span>
						</td>
						<td>
							<a href="#nowhere" class="deleteOrderItem" oiid="${oi.id}"></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="cartFoot">
		<img selectit="false" class="selectAllItem" src="img/site/cartNotSelected.png">
		<span>全选</span>

		<div class="pull-right">
			<span>已选商品 <span class="cartSumNumber">0</span> 件</span>
			<span>合计（不含运费）：</span>
			<span class="cartSumPrice">0.00</span>
			<button class="createOrderButton" disabled="disabled">结 算</button>
		</div>
	</div>
</div>