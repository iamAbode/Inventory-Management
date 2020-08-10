package com.rapidbite.ims.rest;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rapidbite.ims.entity.RequestReq;

@Controller
public class ProcessOrder
{
	ArrayList<RequestReq> itemsReq; 
	
	@PostMapping("/Show-Requisition")
	public String getRequistion(HttpServletRequest request){		
		String result = request.getParameter("cart_list");	
		
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			RequestReq[] pp1 = mapper.readValue(result, RequestReq[].class);
			HttpSession session = request.getSession();
			session.setAttribute("items", pp1);
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return "other_info";
	}
	
	@PostMapping("/Show-Order")
	public String getOrder(HttpServletRequest request){		
		String result = request.getParameter("cart_list");	
		
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			RequestReq[] pp1 = mapper.readValue(result, RequestReq[].class);
			HttpSession session = request.getSession();
			session.setAttribute("orders", pp1);
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return "more_info";
	}
	
	@GetMapping("/Shows-req")
	public String getDetail(){
		return "other_info";
	}
}
