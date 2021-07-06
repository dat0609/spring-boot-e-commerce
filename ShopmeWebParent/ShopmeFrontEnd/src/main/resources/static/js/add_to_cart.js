$(document).ready(function() {
	$("#btnAdd").on("click", function() {
		addToCart()
	}) 
		
})

function addToCart() {
	quantity = $("#quantity" + productId).val()
	url = contextPath + "cart/add/" + productId + "/" + quantity

	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue)
		}
	}).done(function(response) {
		alert(response)
	}).fail(function() {
		alert("Fail to add")
	})
}