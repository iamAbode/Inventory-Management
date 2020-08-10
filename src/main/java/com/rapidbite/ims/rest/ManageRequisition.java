package com.rapidbite.ims.rest;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rapidbite.ims.dao.RequisitionRepo;
import com.rapidbite.ims.entity.RequestReq;
import com.rapidbite.ims.entity.Requisition;

import ng.rapidbite.Pagination;


@RestController
public class ManageRequisition
{
	@Autowired
	private RequisitionRepo reqRepo;
	
	Page<Requisition> allRequisition;
	
	Map hashMap = new HashMap();
	Map rtype = new HashMap();
	Map status = new HashMap();
	
	
	DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
	DateFormat dateFormat2 = new SimpleDateFormat("E, dd MMM yyyy");	
	
	@PostMapping("/api/capreq")	
	public String storeReq(@RequestParam("reason") String reason, @RequestParam("req_type") String reqType, HttpServletRequest request) {
		HttpSession session = request.getSession();
		RequestReq[] pp1 = (RequestReq[]) session.getAttribute("items");		
		
		Long lcode = new Date().getTime();
		String reqId = "R"+lcode.toString();
		String reqOfficer = session.getAttribute("user_id").toString();
		String reqOfficerName = session.getAttribute("firstname").toString()+" "+session.getAttribute("lastname").toString();
		String dept = session.getAttribute("dept").toString();
		
        for (RequestReq p : pp1) {
        	Requisition req = new Requisition();
        	req.setReqId(reqId);	req.setCode(p.getProduct_id()); req.setName(p.getProduct_name());
        	req.setCategory(p.getProduct_desc());  req.setQuantity(p.getProduct_quantity());
        	req.setPrice(p.getProduct_price());    req.setReason(reason); req.setReqType(reqType);
        	req.setStatus("P");  req.setReqOfficer(reqOfficer); req.setReqName(reqOfficerName);
        	req.setDepartment(dept);  req.setAuthOfficer(""); req.setAuthName(""); req.setComment("");
        	req.setComStatus(""); 
        	reqRepo.save(req);         	
           
        }
		
		return "ok";
		
	}
	
	
	
	@GetMapping("/api/allreq")
	public String getItems(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("keywords") String keywords, HttpServletRequest request){
		String results = ""; 		
		rtype.put("U","Urgent");   rtype.put("N","Not Urgent");	
		status.put("P","Pending"); status.put("S","Submitted"); status.put("R","Rejected"); status.put("A","Approved");
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
		HttpSession session = request.getSession();
		String dept_id = session.getAttribute("dept").toString();
		
		if(keywords.equals("") && !dept_id.equals("IMSPA123")) {
			allRequisition = reqRepo.findAll(pageable);
		}
		else if(dept_id.equals("IMSPA123")) {
			allRequisition = reqRepo.findByStatusTerm("S", "A", pageable);	
		}
		else {
			allRequisition = reqRepo.findBySearchTerm(keywords, pageable);	
		}
		ArrayList<String> uniqueId = new ArrayList<String>();
		if(!allRequisition.isEmpty()) {
		int count = 0;		
		results += "<table class='table table-hover table-striped'><thead><tr>"
				+ "<th>SN</th><th>ID</th><th>REASON</th><th>OFFICER</th><th>TYPE</th><th>STATUS</th><th>DATE REQUESTED</th>"
				+ "<th>ACTION</th></tr></thead>";		
		for(Requisition t: allRequisition){ 
			if(!uniqueId.contains(t.getReqId())) {
				uniqueId.add(t.getReqId());
			count++;				
			String date_created = dateFormat.format(Date.from( t.getDateCreated().atZone( ZoneId.systemDefault()).toInstant()));
		results += "<tr><td>"+count+"</td><td>"+t.getReqId()+"</td><td>"+t.getReason()+"</td><td>"+t.getReqName()+"</td><td>"+rtype.get(t.getReqType())+"</td><td>"+status.get(t.getStatus())+"</td>"
				+ "<td>"+date_created+"</td><td><a title='View Item' href='"+request.getContextPath()+"/View-Requisition/"+t.getReqId()+"' class='glyphicon glyphicon-eye-open'></a></td></tr>";   
			}
		}
		results += "</table>";			 
		hashMap.put("totalRows", uniqueId.size());
        hashMap.put("perPage", limit);
        hashMap.put("filter", "searchFilter");       
        hashMap.put("currentPage", page);        
		Pagination pagination = new Pagination(hashMap);
		results += pagination.paginate();			
		}
		else {
			results += "<center><h2> Data not available </h2></center>";
		}		
		return results;		
	}
	
	@PostMapping("/api/findbyreqid")
	public String getreqId(@RequestParam("id") String id, HttpServletRequest request) {		
		String res = "";
		HttpSession session = request.getSession();
		String dept_id = session.getAttribute("dept").toString();
		if(rtype.isEmpty()) { rtype.put("U","Urgent");   rtype.put("N","Not Urgent"); }
		if(status.isEmpty()) { status.put("P","Pending"); status.put("S","Submitted"); status.put("R","Rejected"); status.put("A","Approved");}
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());		
		allRequisition = reqRepo.findByReqId(id, pageable);
		Optional<Requisition> firstElt = allRequisition.get().findFirst();
		Requisition item = null;
		if(firstElt.isPresent()) {
			item = firstElt.get();
			res +="<div class='row'>";	
			res += "<div class='col-md-4'><font color='black'>Requisition ID</font> <br>"+item.getReqId()+"</div>";
			res += "<div class='col-md-4'><font color='black'>Reason</font> <br>"+rtype.get(item.getReqType())+"</div>";
			res += "<div class='col-md-4'><font color='black'>Status</font> <br>"+status.get(item.getStatus())+"</div>";
			res += "<div class='col-md-4'><font color='black'>Reason</font> <br>"+item.getReason()+"</div>";
			res +="</div>";
		}	
				
		for(Requisition t: allRequisition){
			res +="<hr>";
			res +="<div class='row'>";
			res += "<div class='col-md-4'><font color='black'>Item ID</font> <br>"+t.getCode()+"</div>";
			res += "<div class='col-md-4'><font color='black'>Item Name </font> <br>"+t.getName()+"</div>";
			res += "<div class='col-md-4'><font color='black'>Quantity</font> <br>"+t.getQuantity()+"</div>";
			res += "<div class='col-md-4'><font color='black'>Price </font> <br>"+t.getPrice()+"</div>";		
			res +="</div>";			
		}	
		if(dept_id.equals("IMSPA123")) {
		res += "<a title='Reject' href='javascript:void(0);' class='btn btn-danger' onclick=\" reject('"+id+"')\">Reject</a>";
		res += "<a title='Approved' href='javascript:void(0);' class='btn btn-primary' onclick=\" submit('"+id+"','A')\">Receive</a>";
		}
		else {
			res += "<a title='Reject' href='javascript:void(0);' class='btn btn-warning' onclick=\" reject('"+id+"')\">Cancel</a>";
			res += "<a title='Submit' href='javascript:void(0);' class='btn btn-default' onclick=\" submit('"+id+"','S')\">Submit</a>";	
		}
		
		return res;
	}
	
	@GetMapping("/api/upreq/{id}/{st}")	
	public String updateByReqId(@PathVariable String id, @PathVariable String st) {
		int ret = reqRepo.updateReq(st, id);
		String res = "";
		if(ret != 0) {
			res = "ok";
		}
		else {
			res = "ok";
		}
		return res;
	}
	
	

}
