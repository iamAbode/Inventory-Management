package com.rapidbite.ims.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rapidbite.ims.dao.RequisitionRepo;
import com.rapidbite.ims.entity.ItemRequest;
import com.rapidbite.ims.entity.Requisition;

@RestController
public class ManageNotification
{
	@Autowired
	private RequisitionRepo reqRepo;
	
	
	
	@GetMapping("/api/showalert")
	public String getNofication(HttpServletRequest request) { //System.out.println("It got to notification");
		HttpSession session = request.getSession();
		String result = "";
		String dept = session.getAttribute("dept").toString();
		String role = session.getAttribute("role").toString();
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
		//Set<ItemRequest> requesters = new HashSet<ItemRequest>();
		Set<String> req = new HashSet<String>();		
		 if(role.equals("AU")) {			
			Page<Requisition> items =reqRepo.findByStatus(dept, "P", pageable);
			
			for(Requisition t: items){					
				req.add(t.getReqId());						
			}			
			
			System.out.println("Set size: "+req.size());
			result +="<a href='javascript:;' class='dropdown-toggle info-number' data-toggle='dropdown' aria-expanded='false'><i class='fa fa-calendar-minus-o'></i>\r\n" + 
					"<span class='badge bg-red'>"+req.size()+"</span> </a>\r\n";			
			
			result += "<ul id='menu1' class='dropdown-menu list-unstyled msg_list' role='menu'>";
			for(String s: req) {				
				result += "<li><a href='#'><span class='image'>New Requisition: <font color='blue'>"+s+" </font><i class='fa fa-calendar-minus-o'></i></span>";
				result += "<span><span> </span><span class='time'></span></span>";
				result += "<span class='message'>Pending </span> </a> </li>";
				
						}                          
            
            result += "</ul>";			
		}				
			
		return result;
	}
}
