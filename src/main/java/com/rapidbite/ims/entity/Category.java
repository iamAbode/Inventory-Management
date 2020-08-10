package com.rapidbite.ims.entity;

import java.util.Date;
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
@Table(name="category")
public class Category
{
	@Id
	@Column(name="indexer")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
		
	private String code;
	
	@NotEmpty(message = "Please enter category.")	
	private String name;
	
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
			this.code = "C"+nxtnum+UUID.randomUUID().toString().substring(0, 6).toUpperCase();
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
