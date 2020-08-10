package com.rapidbite.ims.entity;

import java.io.Serializable;

public class RequestReq implements Serializable
{	
	private String product_id;

	private String product_name;
	
	private String product_desc;
	
	private int product_quantity;
	
	private double product_price;
	
	private String unique_key;

	public String getProduct_id()
	{
		return product_id;
	}

	public void setProduct_id(String product_id)
	{
		this.product_id = product_id;
	}

	public String getProduct_name()
	{
		return product_name;
	}

	public void setProduct_name(String product_name)
	{
		this.product_name = product_name;
	}

	public String getProduct_desc()
	{
		return product_desc;
	}

	public void setProduct_desc(String product_desc)
	{
		this.product_desc = product_desc;
	}

	public int getProduct_quantity()
	{
		return product_quantity;
	}

	public void setProduct_quantity(int product_quantity)
	{
		this.product_quantity = product_quantity;
	}

	public double getProduct_price()
	{
		return product_price;
	}

	public void setProduct_price(double product_price)
	{
		this.product_price = product_price;
	}

	public String getUnique_key()
	{
		return unique_key;
	}

	public void setUnique_key(String unique_key)
	{
		this.unique_key = unique_key;
	}

	
	

}
