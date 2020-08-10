package com.rapidbite.ims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="orders")
public class Order
{
	@Id
	@Column(name="indexer")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="order_id")
	private String ordId;
	
	@Column(name="item_code")
	private String code;
	
	@Column(name="item_name")
	private String name;
	
	@Column(name="item_cat")
	private String category;
	
	private int quantity;
	
	private double price;
	
	@Column(name="total_price")
	private double total;
	
	@Column(name="supplier_code")
	private String supCode;
	
	private String status;
	
	@Column(name="pur_officer")
	private String purOfficer;
	
	@Column(name="pur_name")
	private String purName;
	
	private String department;
	
	@Column(name="auth_officer")
	private String authOfficer;
	
	@Column(name="auth_name")
	private String authName;
			
	@Column(name="date_ordered")
	@CreationTimestamp
	private java.time.LocalDateTime ordDate;
	
	@Column(name="date_fulfil")
	@UpdateTimestamp
	private java.time.LocalDateTime dateCreated;
	
	@Column(name="date_updated")
	@UpdateTimestamp
	private java.time.LocalDateTime dateUpdated;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getOrdId()
	{
		return ordId;
	}

	public void setOrdId(String ordId)
	{
		this.ordId = ordId;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public double getTotal()
	{
		return total;
	}

	public void setTotal(double total)
	{
		this.total = total;
	}

	public String getSupCode()
	{
		return supCode;
	}

	public void setSupCode(String supCode)
	{
		this.supCode = supCode;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getPurOfficer()
	{
		return purOfficer;
	}

	public void setPurOfficer(String purOfficer)
	{
		this.purOfficer = purOfficer;
	}

	public String getPurName()
	{
		return purName;
	}

	public void setPurName(String purName)
	{
		this.purName = purName;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getAuthOfficer()
	{
		return authOfficer;
	}

	public void setAuthOfficer(String authOfficer)
	{
		this.authOfficer = authOfficer;
	}

	public String getAuthName()
	{
		return authName;
	}

	public void setAuthName(String authName)
	{
		this.authName = authName;
	}

	public java.time.LocalDateTime getOrdDate()
	{
		return ordDate;
	}

	public void setOrdDate(java.time.LocalDateTime ordDate)
	{
		this.ordDate = ordDate;
	}

	public java.time.LocalDateTime getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(java.time.LocalDateTime dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public java.time.LocalDateTime getDateUpdated()
	{
		return dateUpdated;
	}

	public void setDateUpdated(java.time.LocalDateTime dateUpdated)
	{
		this.dateUpdated = dateUpdated;
	}

	
	
	
}
