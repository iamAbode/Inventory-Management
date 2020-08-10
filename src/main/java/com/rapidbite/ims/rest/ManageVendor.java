package com.rapidbite.ims.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rapidbite.ims.dao.VendorRepo;
import com.rapidbite.ims.entity.Vendor;

import ng.rapidbite.Pagination;

@RestController
public class ManageVendor
{
	@Autowired
	private VendorRepo venRepo;
	Page<Vendor> allVen;
	DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");	
	Map hashMap = new HashMap();
	
	@GetMapping("/api/vendors")
	public String getAllDepts(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("keywords") String keywords){
		 String results = ""; 
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());	
		if(keywords.equals("")) {
		allVen = venRepo.findAll(pageable);
		}
		else {
		allVen = venRepo.findBySearchTerm(keywords, pageable);	
		}
		
		if(!allVen.isEmpty()) {
		int count = 0;		
		results += "<table class='table table-hover table-striped'><thead><tr>"
				+ "<th>SN</th><th>CODE</th><th>NAME</th><th>DATE CREATED</th><th>DATE UPDATED</th>"
				+ "<th>ACTION</th></tr></thead>";		
		for(Vendor d: allVen){
			count++;			
			String date_created = dateFormat.format(Date.from( d.getDateCreated().atZone( ZoneId.systemDefault()).toInstant()));
			String date_updated = dateFormat.format(Date.from( d.getDateUpdate().atZone( ZoneId.systemDefault()).toInstant()));
		results += "<tr><td>"+count+"</td><td>"+d.getCode()+"</td><td>"+d.getName()+"</td><td>"+date_created+" </td>"
				+ "<td>"+date_updated+"</td><td><a title='Edit Vendor' href='javascript:void(0);' class='glyphicon glyphicon-edit' onclick=\" findVen('"+d.getId()+"')\"></a>"
						+ "<a title='Delete Vendor' href='javascript:void(0);' class='glyphicon glyphicon-trash' onclick=\" userAction('delete','"+d.getId()+"')\"></a></td></tr>";   
		}
		results += "</table>";			 
		hashMap.put("totalRows", allVen.getTotalElements());
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
	
	@GetMapping("/api/findven/{id}")	
	public Vendor findById(@PathVariable int id) {
		Optional<Vendor> result = venRepo.findById(id);
		Vendor department = null;
		if(result.isPresent()) {
			department = result.get();
		}		
		return department;
	}
	
	@PostMapping("/api/adven")	
	@ResponseBody
	public String addDept(@ModelAttribute @Valid Vendor ven, BindingResult result) {
		String ret = "";
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			ret = errors.get(0).getDefaultMessage();
		}
		else {
			venRepo.save(ven);
			ret = "ok";
		}		
		return ret;
		
	}
	
	@PostMapping("/api/upven")	
	public String upDept(@RequestBody @Valid @ModelAttribute Vendor ven, BindingResult result) { 
		String ret = "";
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			ret = errors.get(0).getDefaultMessage();
		}
		else {
			Optional<Vendor> res = venRepo.findById(ven.getId());		
			ven.setDateCreated(res.get().getDateCreated());
			ven.setDateUpdate(res.get().getDateUpdate());			
			venRepo.save(ven);		
			ret = "ok";
		}			
		return ret;		
	}
			
	@PostMapping("/api/delven/{id}")
	public String deleteDept(@PathVariable int id) {
		String ret = "";
		Optional<Vendor> res = venRepo.findById(id);
		
		if (res.isPresent()) {
			venRepo.deleteById(id);
			ret = "ok";			
		}
		else {
			ret = "Vendor not found";
		}
		
		return ret;
	}	
	
	@GetMapping("/api/allven")
	public List<Vendor> getVendors(){
		return venRepo.findAll();
	}
}
