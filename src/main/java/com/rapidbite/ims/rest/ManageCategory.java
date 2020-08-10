package com.rapidbite.ims.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

import com.rapidbite.ims.dao.CategoryRepo;
import com.rapidbite.ims.entity.Category;

import ng.rapidbite.Pagination;

@RestController
public class ManageCategory
{
	@Autowired
	private CategoryRepo catRepo;
	Page<Category> allcat;
	DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");	
	Map hashMap = new HashMap();
	
	@GetMapping("/api/categories")
	public String getAllcats(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("keywords") String keywords){
		String results = ""; 
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());	
		if(keywords.equals("")) {
		allcat = catRepo.findAll(pageable);
		}
		else {
		allcat = catRepo.findBySearchTerm(keywords, pageable);	
		}
		
		if(!allcat.isEmpty()) {
		int count = 0;		
		results += "<table class='table table-hover table-striped'><thead><tr>"
				+ "<th>SN</th><th>CODE</th><th>NAME</th><th>DATE CREATED</th><th>DATE UPDATED</th>"
				+ "<th>ACTION</th></tr></thead>";		
		for(Category d: allcat){
			count++;			
			String date_created = dateFormat.format(Date.from( d.getDateCreated().atZone( ZoneId.systemDefault()).toInstant()));
			String date_updated = dateFormat.format(Date.from( d.getDateUpdate().atZone( ZoneId.systemDefault()).toInstant()));
		results += "<tr><td>"+count+"</td><td>"+d.getCode()+"</td><td>"+d.getName()+"</td><td>"+date_created+" </td>"
				+ "<td>"+date_updated+"</td><td><a title='Edit Category' href='javascript:void(0);' class='glyphicon glyphicon-edit' onclick=\" findcat('"+d.getId()+"')\"></a>"
						+ "<a title='Delete Category' href='javascript:void(0);' class='glyphicon glyphicon-trash' onclick=\" userAction('delete','"+d.getId()+"')\"></a></td></tr>";   
		}
		results += "</table>";			 
		hashMap.put("totalRows", allcat.getTotalElements());
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
	
	@GetMapping("/api/findcat/{id}")	
	public Category findById(@PathVariable int id) {
		Optional<Category> result = catRepo.findById(id);
		Category Category = null;
		if(result.isPresent()) {
			Category = result.get();
		}		
		return Category;
	}
	
	@PostMapping("/api/adcat")	
	@ResponseBody
	public String addcat(@ModelAttribute @Valid Category cat, BindingResult result) {
		String ret = "";
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			ret = errors.get(0).getDefaultMessage();
		}
		else {
			catRepo.save(cat);
			ret = "ok";
		}		
		return ret;
		
	}
	
	@PostMapping("/api/upcat")	
	public String upcat(@RequestBody @Valid @ModelAttribute Category cat, BindingResult result) { 
		String ret = "";
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			ret = errors.get(0).getDefaultMessage();
		}
		else {
			Optional<Category> res = catRepo.findById(cat.getId());		
			cat.setDateCreated(res.get().getDateCreated());
			cat.setDateUpdate(res.get().getDateUpdate());			
			catRepo.save(cat);		
			ret = "ok";
		}			
		return ret;		
	}
			
	@PostMapping("/api/delcat/{id}")
	public String deletecat(@PathVariable int id) {
		String ret = "";
		Optional<Category> res = catRepo.findById(id);
		
		if (res.isPresent()) {
			catRepo.deleteById(id);
			ret = "ok";			
		}
		else {
			ret = "Category not found";
		}
		
		return ret;
	}	
	
	@GetMapping("/api/allcat")
	public List<Category> getCategory(){
		return catRepo.findAll();
	}
}
