package com.rapidbite.ims.entity;

import java.sql.Date;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="items")
public class Item
{
	@Id
	@Column(name="indexer")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String code;
	
	@NotEmpty(message = "Item name field cannot be empty")
	private String name;
	
	private String category;
	
	private double price;
	
	private int balance;
	
	@Column(name="min_level")
	private int minLevel;
	
	@Column(name="max_level")
	private int maxLevel;
	
	private int reorder;
	
	private int eoq;
	
	@Column(name="date_last_purchased")
	private Date lastPurchased;
	
	@Column(name="date_last_supplied")
	private Date lastSupplied;
	
	@Column(name="last_supplier_code")
	private String supplierCode;
	
	@Column(name="date_created")
	@CreationTimestamp
	private java.time.LocalDateTime dateCreated;
	
	@Column(name="date_updated")
	@UpdateTimestamp
	private java.time.LocalDateTime dateUpdate;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		if(code.isEmpty()) {
			int nxtnum = new Random().nextInt(200);
			this.code = nxtnum+UUID.randomUUID().toString().substring(0, 6).toUpperCase();
		}else { this.code = code; }
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

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public int getBalance()
	{
		return balance;
	}

	public void setBalance(int balance)
	{
		this.balance = balance;
	}

	public int getMinLevel()
	{
		return minLevel;
	}

	public void setMinLevel(int minLevel)
	{
		this.minLevel = minLevel;
	}

	public int getMaxLevel()
	{
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel)
	{
		this.maxLevel = maxLevel;
	}

	public int getReorder()
	{
		return reorder;
	}

	public void setReorder(int reorder)
	{
		this.reorder = reorder;
	}

	public int getEoq()
	{
		return eoq;
	}

	public void setEoq(int eoq)
	{
		this.eoq = eoq;
	}

	public Date getLastPurchased()
	{
		return lastPurchased;
	}

	public void setLastPurchased(Date lastPurchased)
	{
		this.lastPurchased = lastPurchased;
	}

	public Date getLastSupplied()
	{
		return lastSupplied;
	}

	public void setLastSupplied(Date lastSupplied)
	{
		this.lastSupplied = lastSupplied;
	}

	public String getSupplierCode()
	{
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode)
	{
		this.supplierCode = supplierCode;
	}

	public java.time.LocalDateTime getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(java.time.LocalDateTime dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public java.time.LocalDateTime getDateUpdate()
	{
		return dateUpdate;
	}

	public void setDateUpdate(java.time.LocalDateTime dateUpdate)
	{
		this.dateUpdate = dateUpdate;
	}
	
	
}
