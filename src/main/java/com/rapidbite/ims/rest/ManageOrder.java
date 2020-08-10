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

import com.rapidbite.ims.dao.OrderRepo;
import com.rapidbite.ims.dao.RequisitionRepo;
import com.rapidbite.ims.entity.Order;
import com.rapidbite.ims.entity.RequestReq;
import com.rapidbite.ims.entity.Requisition;

import ng.rapidbite.Pagination;


@RestController
public class ManageOrder
{
	@Autowired
	private OrderRepo reqRepo;
	
	Page<Order> allRequisition;
	
	Map hashMap = new HashMap();
	Map status = new HashMap();
	
	
	DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
	DateFormat dateFormat2 = new SimpleDateFormat("E, dd MMM yyyy");	
	
	@PostMapping("/api/capord")	
	public String storeReq(@RequestParam("scode") String scode, HttpServletRequest request) {
		HttpSession session = request.getSession();
		RequestReq[] pp1 = (RequestReq[]) session.getAttribute("orders");		
		
		Long lcode = new Date().getTime();
		String reqId = "O"+lcode.toString();
		String reqOfficer = session.getAttribute("user_id").toString();
		String reqOfficerName = session.getAttribute("firstname").toString()+" "+session.getAttribute("lastname").toString();
		String dept = session.getAttribute("dept").toString();
		double total = 0.0;
        for (RequestReq p : pp1) {
        	Order req = new Order();
        	req.setOrdId(reqId);	req.setCode(p.getProduct_id()); req.setName(p.getProduct_name());
        	req.setCategory(p.getProduct_desc());  req.setQuantity(p.getProduct_quantity());
        	req.setPrice(p.getProduct_price());  total = total + p.getProduct_price();  req.setTotal(total);
        	req.setSupCode(scode);
        	req.setStatus("P");  req.setPurOfficer(reqOfficer); req.setPurName(reqOfficerName);
        	req.setDepartment(dept);  req.setAuthOfficer(""); req.setAuthName("");         	
        	reqRepo.save(req);        
        }
		
		return "ok";		
	}
	
	@GetMapping("/api/allord")
	public String getItems(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("keywords") String keywords, HttpServletRequest request){
		String results = ""; 	
		
		status.put("P","Pending"); status.put("S","Ordered"); status.put("R","Rejected"); status.put("A","Approved");
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
		HttpSession session = request.getSession();
		String dept_id = session.getAttribute("dept").toString();
		
		if(keywords.equals("")) {
			allRequisition = reqRepo.findAll(pageable);
		}		
		else {
			allRequisition = reqRepo.findBySearchTerm(keywords, pageable);	
		}
		ArrayList<String> uniqueId = new ArrayList<String>();
		if(!allRequisition.isEmpty()) {
		int count = 0;		
		results += "<table class='table table-hover table-striped'><thead><tr>"
				+ "<th>SN</th><th>ID</th><th>TOTAL</th><th>OFFICER</th><th>SUPPLIER CODE</th><th>STATUS</th><th>DATE ORDERED</th>"
				+ "<th>ACTION</th></tr></thead>";		
		for(Order t: allRequisition){ 
			if(!uniqueId.contains(t.getOrdId())) {
				uniqueId.add(t.getOrdId());
			count++;				
			String date_created = dateFormat.format(Date.from( t.getDateCreated().atZone( ZoneId.systemDefault()).toInstant()));
		results += "<tr><td>"+count+"</td><td>"+t.getOrdId()+"</td><td>"+t.getTotal()+"</td><td>"+t.getPurName()+"</td><td>"+t.getSupCode()+"</td><td>"+status.get(t.getStatus())+"</td>"
				+ "<td>"+date_created+"</td><td><a title='View Item' href='"+request.getContextPath()+"/View-Order/"+t.getOrdId()+"' class='glyphicon glyphicon-eye-open'></a></td></tr>";   
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
	
	@PostMapping("/api/findbyordid")
	public String getreqId(@RequestParam("id") String id, HttpServletRequest request) {		
		String res = "";
		HttpSession session = request.getSession();
		String dept_id = session.getAttribute("dept").toString();
		String role = session.getAttribute("role").toString();
		
		if(status.isEmpty()) { status.put("P","Pending"); status.put("S","Submitted"); status.put("R","Rejected"); status.put("A","Approved");}
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());		
		allRequisition = reqRepo.findByOrdId(id, pageable);
		Optional<Order> firstElt = allRequisition.get().findFirst();
		Order item = null;
		if(firstElt.isPresent()) {
			item = firstElt.get();
			res +="<div class='row'>";	
			res += "<div class='col-md-4'><font color='black'>Order ID</font> <br>"+item.getOrdId()+"</div>";			
			res += "<div class='col-md-4'><font color='black'>Status</font> <br>"+status.get(item.getStatus())+"</div>";
			res += "<div class='col-md-4'><font color='black'>Supplier Code</font> <br>"+item.getSupCode()+"</div>";
			res +="</div>";
		}	
				
		for(Order t: allRequisition){
			res +="<hr>";
			res +="<div class='row'>";
			res += "<div class='col-md-4'><font color='black'>Item ID</font> <br>"+t.getCode()+"</div>";
			res += "<div class='col-md-4'><font color='black'>Item Name </font> <br>"+t.getName()+"</div>";
			res += "<div class='col-md-4'><font color='black'>Quantity</font> <br>"+t.getQuantity()+"</div>";
			res += "<div class='col-md-4'><font color='black'>Price </font> <br>"+t.getPrice()+"</div>";		
			res +="</div>";			
		}	
		if(dept_id.equals("IMSPA123") && role.equals("AU")) {
		res += "<a title='Reject' href='javascript:void(0);' class='btn btn-danger' onclick=\" reject('"+id+"')\">Cancel</a>";
		res += "<a title='Approved' href='javascript:void(0);' class='btn btn-primary' onclick=\" submit('"+id+"','A')\">Approved</a>";
		}
		else {
			//res += "<a title='Reject' href='javascript:void(0);' class='btn btn-warning' onclick=\" reject('"+id+"')\">Cancel</a>";
			//res += "<a title='Submit' href='javascript:void(0);' class='btn btn-default' onclick=\" submit('"+id+"','S')\">Submit</a>";	
		}
		
		return res;
	}
	
	@GetMapping("/api/upord/{id}/{st}")	
	public String updateByReqId(@PathVariable String id, @PathVariable String st) {
		int ret = reqRepo.updateReq(st, id);
		String res = "";
		if(ret != 0) {
			res = "ok";
		}
		else {
			res = "nok";
		}
		return res;
	}
	
	@GetMapping("/api/findbyorder")
	public String getPurchase(@RequestParam("keywords") String keywords, HttpServletRequest request) {		
		String res = "";
		HttpSession session = request.getSession();
		String dept_id = session.getAttribute("dept").toString();
		String role = session.getAttribute("role").toString();
		
		if(status.isEmpty()) { status.put("A","Ordered"); status.put("V","Validated"); status.put("RE","Received"); }
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());		
		allRequisition = reqRepo.findByOrderTerm(keywords, "A", pageable);
		Optional<Order> firstElt = allRequisition.get().findFirst();
		Order item = null;
		
		if(firstElt.isPresent()) {
			item = firstElt.get();
			res +="<div class='row'>";	
			res += "<div class='col-md-4'><font color='black'>Order ID</font> <br>"+item.getOrdId()+"</div>";			
			res += "<div class='col-md-4'><font color='black'>Status</font> <br>"+status.get(item.getStatus())+"</div>";
			res += "<div class='col-md-4'><font color='black'>Supplier Code</font> <br>"+item.getSupCode()+"</div>";
			res +="</div>";
			for(Order t: allRequisition){
				res +="<hr>";
				res +="<div class='row'>";
				res += "<div class='col-md-2'><font color='black'>Item ID</font> <br>"+t.getCode()+"</div>";
				res += "<div class='col-md-4'><font color='black'>Item Name </font> <br>"+t.getName()+"</div>";
				res += "<div class='col-md-2'><font color='black'>Quantity</font> <br>"+t.getQuantity()+"</div>";
				res += "<div class='col-md-2'><font color='black'>Price </font> <br>"+t.getPrice()+"</div>";
				res += "<div class='col-md-3'><font color='black'>Status </font> <br>"+status.get(t.getStatus())+"</div>";
				res += "<div class='col-md-2'><a title='Validate' href='javascript:void(0);' class='btn btn-danger' onclick=\" submit('"+item.getOrdId()+"','"+t.getCode()+"','V')\">Pend</a></div>";
				res += "<div class='col-md-2'><a title='Approved Receipt' href='javascript:void(0);' class='btn btn-primary' onclick=\" submit('"+item.getOrdId()+"','"+t.getCode()+"','RE')\">Approved Receipt</a></div>";
				res +="</div>";			
			}
		}
		else {
			res += "<br><br><div><font color='red'>Invalid PO, PO already Fulfilled or PO is Canceled</font></div><br><br>";
		}
				
			
				
		return res;
	}
	
	@GetMapping("/api/upitm/{id}/{itd}/{st}")	
	public String updateByitem(@PathVariable String id, @PathVariable String itd, @PathVariable String st) {
		int ret = reqRepo.updateItem(id, itd, st);
		String res = "";
		if(ret != 0) {
			res = "ok";
		}
		else {
			res = "nok";
		}
		return res;
	}
	
	@GetMapping("/api/storeitem")
	public String getStore(@RequestParam("keywords") String keywords, HttpServletRequest request) {		
		String res = "";
		HttpSession session = request.getSession();
		String dept_id = session.getAttribute("dept").toString();
		String role = session.getAttribute("role").toString();
		
		if(status.isEmpty()) { status.put("A","Ordered"); status.put("V","Validated"); status.put("RE","Received"); status.put("SR","Stored"); }
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());		
		allRequisition = reqRepo.findByOrderTerm(keywords, "RE", pageable);
		Optional<Order> firstElt = allRequisition.get().findFirst();
		Order item = null;
		
		if(firstElt.isPresent()) {
			item = firstElt.get();
			res +="<div class='row'>";	
			res += "<div class='col-md-4'><font color='black'>Order ID</font> <br>"+item.getOrdId()+"</div>";			
			res += "<div class='col-md-4'><font color='black'>Status</font> <br>"+status.get(item.getStatus())+"</div>";
			res += "<div class='col-md-4'><font color='black'>Supplier Code</font> <br>"+item.getSupCode()+"</div>";
			res +="</div>";
			for(Order t: allRequisition){
				res +="<hr>";
				res +="<div class='row'>";
				res += "<div class='col-md-2'><font color='black'>Item ID</font> <br>"+t.getCode()+"</div>";
				res += "<div class='col-md-4'><font color='black'>Item Name </font> <br>"+t.getName()+"</div>";
				res += "<div class='col-md-2'><font color='black'>Quantity</font> <br>"+t.getQuantity()+"</div>";
				res += "<div class='col-md-2'><font color='black'>Price </font> <br>"+t.getPrice()+"</div>";
				res += "<div class='col-md-3'><font color='black'>Status </font> <br>"+status.get(t.getStatus())+"</div>";
				res += "<div class='col-md-2'><a title='Store' href='javascript:void(0);' class='btn btn-primary' onclick=\" submit('"+item.getOrdId()+"','"+t.getCode()+"','SR','"+t.getQuantity()+"')\">Store Item</a></div>";
				res +="</div>";			
			}
		}
		else {
			res += "<br><br><div><font color='red'>Goods Supplied is not Yet Approved</font></div><br><br>";
		}
				
			
				
		return res;
	}
	
}
