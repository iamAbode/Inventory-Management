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

import com.rapidbite.ims.dao.DepartmentRepo;
import com.rapidbite.ims.dao.UserRepo;
import com.rapidbite.ims.entity.Department;
import com.rapidbite.ims.entity.User;
import ng.rapidbite.Pagination;

@RestController
public class ManageUser
{
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private DepartmentRepo deptRepo;
	
	Page<User> users;
	DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");	
	Map hashMap = new HashMap();
	Map deps = new HashMap();
	Map roles = new HashMap();
	
	@GetMapping("/api/users")
	public String getUsers(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("keywords") String keywords, @RequestParam("deptId") String deptId){
		String results = ""; 
		if(deps.isEmpty()) {
		List<Department> departments = deptRepo.findAll();
		for(Department d: departments) {
		deps.put(d.getCode(), d.getName());
		}
		}
		
		roles.put("DI", "Initiator"); roles.put("AU", "Authorizer"); roles.put("AD", "Admin");
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());	
		if(keywords.equals("") && deptId.equals("")) {
			users = userRepo.findAll(pageable);
		}
		else if(keywords.equals("") && !deptId.equals("")){
			users = userRepo.findByDept(deptId, pageable);	
		}
		else {
			users = userRepo.findBySearchTerm(keywords, deptId, pageable);	
		}
		
		if(!users.isEmpty()) {
		int count = 0;		
		results += "<table class='table table-hover table-striped'><thead><tr>"
				+ "<th>SN</th><th>FIRSTNAME</th><th>LASTNAME</th><th>DEPARTMENT</th><th>ROLE</th><th>DATE CREATED</th><th>DATE UPDATED</th>"
				+ "<th>ACTION</th></tr></thead>";		
		for(User u: users){ 
			count++;			
			String date_created = dateFormat.format(Date.from( u.getDateCreated().atZone( ZoneId.systemDefault()).toInstant()));
			String date_updated = dateFormat.format(Date.from( u.getDateUpdate().atZone( ZoneId.systemDefault()).toInstant()));
		results += "<tr><td>"+count+"</td><td>"+u.getFirstname()+"</td><td>"+u.getLastname()+"</td><td>"+deps.get(u.getDepartment())+"</td><td>"+roles.get(u.getRole())+"</td><td>"+date_created+" </td>"
				+ "<td>"+date_updated+"</td><td><a title='View User' href='javascript:void(0);' class='glyphicon glyphicon-eye-open' onclick=\"+ viewuser('"+u.getId()+"')\"></a> "
						+ "<a title='Edit User' href='javascript:void(0);' class='glyphicon glyphicon-edit' onclick=\" finduser('"+u.getId()+"')\"></a>"
						+ "<a title='Delete User' href='javascript:void(0);' class='glyphicon glyphicon-trash' onclick=\" userAction('delete','"+u.getId()+"')\"></a></td></tr>";   
		}
		results += "</table>";			 
		hashMap.put("totalRows", users.getTotalElements());
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
	
	@GetMapping("/api/finduser/{id}")	
	public User findById(@PathVariable int id) {
		Optional<User> result = userRepo.findById(id);
		User User = null;
		if(result.isPresent()) {
			User = result.get();
		}		
		return User;
	}
	
	@GetMapping("/api/viewuser/{id}")	
	public String findByIdStr(@PathVariable int id) {
		Optional<User> result = userRepo.findById(id);
		User user = null;
		String res = "";
		if(result.isPresent()) {
			user = result.get();
			String date_created = dateFormat.format(Date.from( user.getDateCreated().atZone( ZoneId.systemDefault()).toInstant()));
			String date_updated = dateFormat.format(Date.from( user.getDateUpdate().atZone( ZoneId.systemDefault()).toInstant()));
	
			res	+= "<table class='table table-hover'><thead><tr>"
					+ "<th>FIRSTNAME</th><th>LASTNAME</th><th>EMAIL</th><th>DEPARTMENT</th><th>ROLE</th><th>DATE CREATED</th><th>DATE UPDATED</th>"
					+ "</tr></thead>";
			
			res	+= "<tr><td>"+user.getFirstname()+"</td><td>"+user.getLastname()+"</td><td>"+user.getEmail()+"</td><td>"+deps.get(user.getDepartment())+"</td><td>"+roles.get(user.getRole())+"</td><td>"+date_created+" </td>"
					+ "<td>"+date_updated+"</td><tr>";
			res	+= "</table>";	
		}		
		return res;
	}
	
	
	@PostMapping("/api/adduser")	
	@ResponseBody
	public String adduser(@ModelAttribute @Valid User user, BindingResult result) {
		String ret = "";
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			ret = errors.get(0).getDefaultMessage();
		}
		else {
			userRepo.save(user);
			ret = "ok";
		}		
		return ret;
		
	}
	
	@PostMapping("/api/upuser")	
	public String upuser(@RequestBody @Valid @ModelAttribute User user, BindingResult result) { 
		String ret = "";
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			ret = errors.get(0).getDefaultMessage();
		}
		else {
			Optional<User> res = userRepo.findById(user.getId());		
			user.setDateCreated(res.get().getDateCreated());
			user.setDateUpdate(res.get().getDateUpdate());			
			userRepo.save(user);		
			ret = "ok";
		}			
		return ret;		
	}
			
	@PostMapping("/api/deluser/{id}")
	public String deleteuser(@PathVariable int id) {
		String ret = "";
		Optional<User> res = userRepo.findById(id);
		
		if (res.isPresent()) {
			userRepo.deleteById(id);
			ret = "ok";			
		}
		else {
			ret = "User not found";
		}
		
		return ret;
	}
}
