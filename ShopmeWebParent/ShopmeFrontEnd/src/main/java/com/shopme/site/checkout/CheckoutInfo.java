package com.shopme.site.checkout;

import java.text.DecimalFormat;
import java.util.Date;

public class CheckoutInfo {

	private float productCost;
	private float productTotal;
	private float shippingCost;
	private float paymentTotal;
	private Date deliverDate;

	public float getProductCost() {
		return productCost;
	}

	public void setProductCost(float productCost) {
		this.productCost = productCost;
	}

	public float getProductTotal() {
		return productTotal;
	}

	public void setProductTotal(float productTotal) {
		this.productTotal = productTotal;
	}

	public float getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(float shippingCost) {
		this.shippingCost = shippingCost;
	}

	public float getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(float paymentTotal) {
		this.paymentTotal = paymentTotal;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	
	public String getPaymentTotalPaypal() {
		DecimalFormat format = new DecimalFormat("###.#");
		return format.format(paymentTotal);
	}

}
