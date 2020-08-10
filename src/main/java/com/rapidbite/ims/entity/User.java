package com.rapidbite.ims.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="users")
public class User
{
	
	@Id
	@Column(name="indexer")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="user_id")
	private String userId;	

	@NotEmpty(message = "Firstname field cannot be empty")
	private String firstname;
	
	@NotEmpty(message = "Lastname field cannot be empty")
	private String lastname;
	
	@NotEmpty(message = "Password field cannot be empty")
	private String password;
	
	@NotEmpty(message = "Email field cannot be empty")
	@Email(message="Invalid email address")
	private String email;
	
	@NotEmpty(message = "Department field cannot be empty")
	private String department;	
	
	private String role;
	
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
	
	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		if(userId.isEmpty()) {
			Long lcode = new Date().getTime();
			this.userId = "ID"+lcode.toString();
		}else { this.userId = userId; }
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
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
