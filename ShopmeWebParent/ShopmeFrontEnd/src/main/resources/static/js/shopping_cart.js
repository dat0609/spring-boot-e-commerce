$(document).ready(function() {
	$(".linkMinus").click(function(e) {
		e.preventDefault()
		decreaseQuantity($(this))

	})

	$(".linkPlus").click(function(e) {
		e.preventDefault()
		increaseQuantity($(this))
	})

	$(".linkRemove").click(function(e) {
		e.preventDefault()
		removeProduct($(this))
	})
})

function decreaseQuantity(link) {
	productId = link.attr("pid")
	quantityInput = $("#quantity" + productId)
	newQuantity = parseInt(quantityInput.val()) - 1

	if (newQuantity > 0) {
		quantityInput.val(newQuantity)
		updateQuantity(productId, newQuantity)
	} else {
		alert("haa")
	}
}

function increaseQuantity(link) {
	productId = link.attr("pid")
	quantityInput = $("#quantity" + productId)
	newQuantity = parseInt(quantityInput.val()) + 1

	if (newQuantity <= 5) {
		quantityInput.val(newQuantity)
		updateQuantity(productId, newQuantity)
	} else {
		alert("haa ++")
	}
}

function updateQuantity(productId, quantity) {

	url = contextPath + "cart/update/" + productId + "/" + quantity

	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue)
		}
	}).done(function(updatedSubtotal) {
		updateSubtotal(updatedSubtotal, productId)
		updateTotal()
	}).fail(function() {
		alert("Fail" + quantity)
	})
}

function updateSubtotal(updatedSubtotal, productId) {
	$("#subtotal" + productId).text(updatedSubtotal)
}

function updateTotal() {
	total = 0

	$(".subtotal").each(function(index, element) {
		total += parseFloat(element.innerHTML)
	})

	$("#total").text(total)
}

function removeProduct(link) {
	url = link.attr("href")
	
		$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue)
		}
	}).done(function(response) {
		alert(response)
		location.reload()
	}).fail(function() {
		alert("Fail")
	})
}