package com.rapidbite.ims.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SideBarRoute
{

	@GetMapping("/Manage-Departments")
	public String getDepartments(){
		return "department";
	}
	
	@GetMapping("/Manage-Users")
	public String getUsers(){
		return "user";
	}
	
	@GetMapping("/Manage-Category")
	public String getCategory(){
		return "category";
	}
	
	@GetMapping("/Manage-Item")
	public String getItem(){
		return "item";
	}
	
	@GetMapping("/Manage-Vendors")
	public String getVendor(){
		return "vendor";
	}
	@GetMapping("/All-Requisition")
	public String getRequisitions(){
		return "all_requisition";
	}
	
	@GetMapping("/Requisition")
	public String getRequisition(){
		return "requisition";
	}
	
	@GetMapping("/Ordering")
	public String getOrder(){
		return "ordering";
	}
	
	@GetMapping("/All-Orders")
	public String getOrders(){
		return "all_order";
	}
	
	@GetMapping("/View-Requisition/{id}")
	public String viewRequisitions(@PathVariable String id, HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("req_id", id);
		return "view_requisition";
	}
	
	@GetMapping("/View-Order/{id}")
	public String viewOrders(@PathVariable String id, HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("req_id", id);
		return "view_order";
	}
	
	@GetMapping("/Receipt-Purchase")
	public String getPurchase(){
		return "purchase";
	}
	
	@GetMapping("/Receive-Goods")
	public String getGoods(){
		return "receipt";
	}
	
	@GetMapping("/home")
	public String getHome(){
		return "home";
	}
}
