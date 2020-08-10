package com.rapidbite.ims.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rapidbite.ims.dao.DepartmentRepo;
import com.rapidbite.ims.entity.Department;

import ng.rapidbite.Pagination;;

@RestController
public class ManageDepartment
{
	@Autowired
	private DepartmentRepo deptRepo;
	Page<Department> allDept;
	DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");	
	Map hashMap = new HashMap();
	
	@GetMapping("/api/departments")
	public String getAllDepts(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("keywords") String keywords){
		 String results = ""; 
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());	
		if(keywords.equals("")) {
		allDept = deptRepo.findAll(pageable);
		}
		else {
		allDept = deptRepo.findBySearchTerm(keywords, pageable);	
		}
		
		if(!allDept.isEmpty()) {
		int count = 0;		
		results += "<table class='table table-hover table-striped'><thead><tr>"
				+ "<th>SN</th><th>CODE</th><th>NAME</th><th>DATE CREATED</th><th>DATE UPDATED</th>"
				+ "<th>ACTION</th></tr></thead>";		
		for(Department d: allDept){
			count++;			
			String date_created = dateFormat.format(Date.from( d.getDateCreated().atZone( ZoneId.systemDefault()).toInstant()));
			String date_updated = dateFormat.format(Date.from( d.getDateUpdate().atZone( ZoneId.systemDefault()).toInstant()));
		results += "<tr><td>"+count+"</td><td>"+d.getCode()+"</td><td>"+d.getName()+"</td><td>"+date_created+" </td>"
				+ "<td>"+date_updated+"</td><td><a title='Edit Department' href='javascript:void(0);' class='glyphicon glyphicon-edit' onclick=\" findDept('"+d.getId()+"')\"></a>"
						+ "<a title='Delete Department' href='javascript:void(0);' class='glyphicon glyphicon-trash' onclick=\" userAction('delete','"+d.getId()+"')\"></a></td></tr>";   
		}
		results += "</table>";			 
		hashMap.put("totalRows", allDept.getTotalElements());
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
	
	@GetMapping("/api/finddept/{id}")	
	public Department findById(@PathVariable int id) {
		Optional<Department> result = deptRepo.findById(id);
		Department department = null;
		if(result.isPresent()) {
			department = result.get();
		}		
		return department;
	}
	
	@PostMapping("/api/addept")	
	@ResponseBody
	public String addDept(@ModelAttribute @Valid Department dept, BindingResult result) {
		String ret = "";
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			ret = errors.get(0).getDefaultMessage();
		}
		else {
			deptRepo.save(dept);
			ret = "ok";
		}		
		return ret;
		
	}
	
	@PostMapping("/api/updept")	
	public String upDept(@RequestBody @Valid @ModelAttribute Department dept, BindingResult result) { 
		String ret = "";
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			ret = errors.get(0).getDefaultMessage();
		}
		else {
			Optional<Department> res = deptRepo.findById(dept.getId());		
			dept.setDateCreated(res.get().getDateCreated());
			dept.setDateUpdate(res.get().getDateUpdate());			
			deptRepo.save(dept);		
			ret = "ok";
		}			
		return ret;		
	}
			
	@PostMapping("/api/deldept/{id}")
	public String deleteDept(@PathVariable int id) {
		String ret = "";
		Optional<Department> res = deptRepo.findById(id);
		
		if (res.isPresent()) {
			deptRepo.deleteById(id);
			ret = "ok";			
		}
		else {
			ret = "Department not found";
		}
		
		return ret;
	}	
	
	@GetMapping("/api/alldept")
	public List<Department> getDepartments(){
		return deptRepo.findAll();
	}
	
}
