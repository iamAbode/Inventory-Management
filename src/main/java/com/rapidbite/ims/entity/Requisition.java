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
@Table(name="requisition")
public class Requisition
{
	@Id
	@Column(name="indexer")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="req_id")
	private String reqId;
	
	@Column(name="item_code")
	private String code;
	
	@Column(name="item_name")
	private String name;
	
	@Column(name="item_cat")
	private String category;
	
	private int quantity;
	
	private double price;
	
	private String reason;
	
	@Column(name="req_type")
	private String reqType;
	
	private String status;
	
	@Column(name="req_officer")
	private String reqOfficer;
	
	@Column(name="req_name")
	private String reqName;
	
	private String department;
	
	@Column(name="auth_officer")
	private String authOfficer;
	
	@Column(name="auth_name")
	private String authName;
	
	private String comment;
	
	@Column(name="com_status")
	private String comStatus;
	
	@Column(name="com_date")
	@UpdateTimestamp
	private java.time.LocalDateTime comDate;
	
	@Column(name="date_created")
	@CreationTimestamp
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

	public String getReqId()
	{
		return reqId;
	}

	public void setReqId(String reqId)
	{
		this.reqId = reqId;
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

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	public String getReqType()
	{
		return reqType;
	}

	public void setReqType(String reqType)
	{
		this.reqType = reqType;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getReqOfficer()
	{
		return reqOfficer;
	}

	public void setReqOfficer(String reqOfficer)
	{
		this.reqOfficer = reqOfficer;
	}

	public String getReqName()
	{
		return reqName;
	}

	public void setReqName(String reqName)
	{
		this.reqName = reqName;
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

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getComStatus()
	{
		return comStatus;
	}

	public void setComStatus(String comStatus)
	{
		this.comStatus = comStatus;
	}

	public java.time.LocalDateTime getComDate()
	{
		return comDate;
	}

	public void setComDate(java.time.LocalDateTime comDate)
	{
		this.comDate = comDate;
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
