package com.photon.phresco.framework.model;

import com.photon.phresco.commons.model.Customer;

public class ClientIdentifyModel {
	
	Customer customer;
	String customerlogo;
	String favIcon;
	String loginIcon;
	Boolean status;
	String message;
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCustomerlogo() {
		return customerlogo;
	}
	public void setCustomerlogo(String customerlogo) {
		this.customerlogo = customerlogo;
	}
	public String getFavIcon() {
		return favIcon;
	}
	public void setFavIcon(String favIcon) {
		this.favIcon = favIcon;
	}
	public String getLoginIcon() {
		return loginIcon;
	}
	public void setLoginIcon(String loginIcon) {
		this.loginIcon = loginIcon;
	}

}
