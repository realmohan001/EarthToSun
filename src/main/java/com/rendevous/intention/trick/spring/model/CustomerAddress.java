package com.rendevous.intention.trick.spring.model;
// Generated Jul 24, 2013 4:36:42 PM by Hibernate Tools 3.6.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CustomerAddress generated by hbm2java
 */
@Entity
@Table(name = "CUSTOMER_ADDRESS", catalog = "cherryDB")
public class CustomerAddress implements java.io.Serializable {

	private Integer addressId;
	private Customers customers;
	private String addressType;
	private String addressFirstName;
	private String addressLastName;
	private String companyName;
	private String addressContactPhoneNumber;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String postalZipcode;
	private String country;

	public CustomerAddress() {
	}

	public CustomerAddress(Customers customers, String addressType,
			String addressFirstName, String addressLastName,
			String companyName, String addressContactPhoneNumber,
			String addressLine1, String addressLine2, String city,
			String state, String postalZipcode, String country) {
		this.customers = customers;
		this.addressType = addressType;
		this.addressFirstName = addressFirstName;
		this.addressLastName = addressLastName;
		this.companyName = companyName;
		this.addressContactPhoneNumber = addressContactPhoneNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.postalZipcode = postalZipcode;
		this.country = country;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ADDRESS_ID", unique = true, nullable = false)
	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customers getCustomers() {
		return this.customers;
	}

	public void setCustomers(Customers customers) {
		this.customers = customers;
	}

	@Column(name = "ADDRESS_TYPE", length = 30)
	public String getAddressType() {
		return this.addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	@Column(name = "ADDRESS_FIRST_NAME", length = 150)
	public String getAddressFirstName() {
		return this.addressFirstName;
	}

	public void setAddressFirstName(String addressFirstName) {
		this.addressFirstName = addressFirstName;
	}

	@Column(name = "ADDRESS_LAST_NAME", length = 150)
	public String getAddressLastName() {
		return this.addressLastName;
	}

	public void setAddressLastName(String addressLastName) {
		this.addressLastName = addressLastName;
	}

	@Column(name = "COMPANY_NAME", length = 150)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "ADDRESS_CONTACT_PHONE_NUMBER", length = 15)
	public String getAddressContactPhoneNumber() {
		return this.addressContactPhoneNumber;
	}

	public void setAddressContactPhoneNumber(String addressContactPhoneNumber) {
		this.addressContactPhoneNumber = addressContactPhoneNumber;
	}

	@Column(name = "ADDRESS_LINE_1", length = 100)
	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	@Column(name = "ADDRESS_LINE_2", length = 100)
	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	@Column(name = "CITY", length = 100)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STATE", length = 100)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "POSTAL_ZIPCODE", length = 15)
	public String getPostalZipcode() {
		return this.postalZipcode;
	}

	public void setPostalZipcode(String postalZipcode) {
		this.postalZipcode = postalZipcode;
	}

	@Column(name = "COUNTRY", length = 100)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
