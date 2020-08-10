package com.rapidbite.ims.rest;

import java.text.DateFormat;
import java.text.NumberFormat;
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

import com.rapidbite.ims.dao.CategoryRepo;
import com.rapidbite.ims.dao.ItemRepo;
import com.rapidbite.ims.entity.Category;
import com.rapidbite.ims.entity.Department;
import com.rapidbite.ims.entity.Item;

import ng.rapidbite.Pagination;

@RestController
public class ManageItem
{
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private CategoryRepo catRepo;
	
	Page<Item> items;
	DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
	DateFormat dateFormat2 = new SimpleDateFormat("E, dd MMM yyyy");	
	
	Map hashMap = new HashMap();
	Map cats = new HashMap();
		
	@GetMapping("/api/items")
	public String getItems(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("keywords") String keywords, @RequestParam("catId") String catId){
		String results = ""; 
		if(cats.isEmpty()) {
		List<Category> categories = catRepo.findAll();
		for(Category d: categories) {
		cats.put(d.getCode(), d.getName());
		}
		}		
			
		Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());	
		if(keywords.equals("") && catId.equals("")) {
			items = itemRepo.findAll(pageable);
		}
		else if(keywords.equals("") && !catId.equals("")){
			items = itemRepo.findByCategory(catId, pageable);	
		}
		else {
			items = itemRepo.findBySearchTerm(keywords, catId, pageable);	
		}
		
		if(!items.isEmpty()) {
		int count = 0;		
		results += "<table class='table table-hover table-striped'><thead><tr>"
				+ "<th>SN</th><th>CODE</th><th>DESCRIPTION</th><th>CATEGORY</th><th>PRICE</th><th>STOCK BALANCE</th><th>REORDER LEVEL</th>"
				+ "<th>ACTION</th></tr></thead>";		
		for(Item t: items){ 
			count++;				
			String price = NumberFormat.getInstance().format(t.getPrice());
		results += "<tr><td>"+count+"</td><td>"+t.getCode()+"</td><td>"+t.getName()+"</td><td>"+cats.get(t.getCategory())+"</td><td>"+price+"</td><td>"+t.getBalance()+"</td>"
				+ "<td>"+t.getReorder()+"</td><td><a title='View Item' href='javascript:void(0);' class='glyphicon glyphicon-eye-open' onclick=\"+ viewitem('"+t.getId()+"')\"></a> "
						+ "<a title='Edit Item' href='javascript:void(0);' class='glyphicon glyphicon-edit' onclick=\" finditem('"+t.getId()+"')\"></a>"
						+ "<a title='Delete Item' href='javascript:void(0);' class='glyphicon glyphicon-trash' onclick=\" userAction('delete','"+t.getId()+"')\"></a></td></tr>";   
		}
		results += "</table>";			 
		hashMap.put("totalRows", items.getTotalElements());
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
	
	@GetMapping("/api/finditem/{id}")	
	public Item findById(@PathVariable int id) {
		Optional<Item> result = itemRepo.findById(id);
		Item Item = null;
		if(result.isPresent()) {
			Item = result.get();
		}		
		return Item;
	}
	
	@GetMapping("/api/viewitem/{id}")	
	public String findByIdStr(@PathVariable int id) {
		Optional<Item> result = itemRepo.findById(id);
		Item item = null;
		String res = "";
		if(result.isPresent()) {
			item = result.get();
			String date_updated = dateFormat.format(Date.from( item.getDateUpdate().atZone( ZoneId.systemDefault()).toInstant()));
			
			String date_purchased = dateFormat2.format(item.getLastPurchased());
			String date_supplied = dateFormat2.format(item.getLastSupplied());
			String price = NumberFormat.getInstance().format(item.getPrice());
			res	+= "<table class='table table-hover'>";
			
			res += "<thead><th>BARCODE</th><th colspan='2'>DESCRIPTION</th><th colspan='2'>CATEGORY</th></thead>";
			res	+= "<tr><td>"+item.getCode()+"</td><td colspan='2'>"+item.getName()+"</td><td colspan='2'>"+cats.get(item.getCategory())+"</td></tr>";
			res	+= "<tr><td colspan='5'></td></tr>";
			res += "<thead><th>UNIT PRICE</th><th>STOCK BALANCE</th><th>MINIMUM LEVEL</th><th>MAXIMUM LEVEL</th><th>REORDER LEVEL</th></thead>";
			res	+= "<tr><td>"+price+"</td><td>"+item.getBalance()+"</td><td>"+item.getMinLevel()+"</td><td>"+item.getMaxLevel()+"</td><td>"+item.getReorder()+"</td></tr>";
			res	+= "<tr><td colspan='5'></td></tr>";
			res += "<thead><th>EOQ </th><th>DATE LAST PURCHASED</th><th>DATE LAST SUPPLIED</th><th>LAST SUPPLIER CODE</th><th>DATE UPDATED</th></thead>";
			res	+= "<tr><td>"+item.getEoq()+"</td><td>"+date_purchased+"</td><td>"+date_supplied+"</td><td>"+item.getSupplierCode()+"</td><td>"+date_updated+"</td></tr>";
			
			res	+= "</table>";	
		}		
		return res;
	}	

	
	@PostMapping("/api/additem")	
	@ResponseBody
	public String adduser(@ModelAttribute @Valid Item item, BindingResult result) {
		String ret = "";
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			ret = errors.get(0).getDefaultMessage();
		}
		else {			
			item.setCode(item.getName().toUpperCase().charAt(0)+item.getCode());
			itemRepo.save(item);
			ret = "ok";
		}		
		return ret;
		
	}
	
	@PostMapping("/api/upitem")	
	public String upuser(@RequestBody @Valid @ModelAttribute Item item, BindingResult result) { 
		String ret = "";
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			ret = errors.get(0).getDefaultMessage();
		}
		else {
			Optional<Item> res = itemRepo.findById(item.getId());		
			item.setDateCreated(res.get().getDateCreated());
			item.setDateUpdate(res.get().getDateUpdate());			
			itemRepo.save(item);		
			ret = "ok";
		}			
		return ret;		
	}
			
	@PostMapping("/api/delitem/{id}")
	public String deleteuser(@PathVariable int id) {
		String ret = "";
		Optional<Item> res = itemRepo.findById(id);
		
		if (res.isPresent()) {
			itemRepo.deleteById(id);
			ret = "ok";			
		}
		else {
			ret = "Item not found";
		}
		
		return ret;
	}
	
	@GetMapping("/api/allitems")	
	public List<Item> findItems() {
		return itemRepo.findAll();		
	}
	
	@GetMapping("/api/upinventory/{id}/{val}")	
	public String updateInventory(@PathVariable String id, @PathVariable int val) {
		int ret = itemRepo.updateItem(id, val);
		String res = "";
		if(ret != 0) {
			res = "ok";
		}
		else {
			res = "nok";
		}
		return res;
	}
}
