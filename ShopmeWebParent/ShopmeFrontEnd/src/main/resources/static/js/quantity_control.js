$(document).ready(function() {
	$(".linkMinus").click(function(e) {
		e.preventDefault()
		productId = $(this).attr("pid")
		quantityInput = $("#quantity" + productId)
		newQuantity = parseInt(quantityInput.val()) - 1
		
		if(newQuantity > 0){
			quantityInput.val(newQuantity)
		}else{
			alert("haa")
		}
	})

	$(".linkPlus").click(function(e) {
		e.preventDefault()
		productId = $(this).attr("pid")
		quantityInput = $("#quantity" + productId)
		newQuantity = parseInt(quantityInput.val()) + 1
		
		if(newQuantity <= 5 ){
			quantityInput.val(newQuantity)
		}else{
			alert("haa ++")
		}
	})
})