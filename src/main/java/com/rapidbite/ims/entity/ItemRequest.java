package com.rapidbite.ims.entity;

public class ItemRequest
{
	private String id;
	
	private String name;
	
	private String department;
	
	private java.time.LocalDateTime date;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public java.time.LocalDateTime getDate()
	{
		return date;
	}

	public void setDate(java.time.LocalDateTime date)
	{
		this.date = date;
	}
	
}
