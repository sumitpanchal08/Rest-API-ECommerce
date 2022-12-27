package com.dealsnow.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dealsnow.models.Admin;
import com.dealsnow.models.AdminDTO;
import com.dealsnow.models.Category;
import com.dealsnow.models.Product;
import com.dealsnow.models.ProductImg;
import com.dealsnow.models.Promocode;
import com.dealsnow.service.AdminService;

@RestController
@RequestMapping("/Admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/register")
	public ResponseEntity<Admin> registerAdmin(@Valid @RequestBody Admin admin){
		return new ResponseEntity<>(adminService.registerAdmin(admin),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/login")
	public ResponseEntity<Admin> loginAdmin(@Valid @RequestBody AdminDTO adminDTO){
		return new ResponseEntity<>(adminService.loginAdmin(adminDTO),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/logout/{userId}")
	public ResponseEntity<String> logoutAdmin(@Valid @PathVariable("userId") Integer userId){
		return new ResponseEntity<String>(adminService.logoutAdmin(userId),HttpStatus.ACCEPTED);
	}
	
	//add new product
	@PostMapping("/product/{catId}")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product p,@PathVariable("catId") Integer catId){
		return new ResponseEntity<Product>(adminService.addProduct(p, catId),HttpStatus.ACCEPTED);
	}
	
	//get all Products available in database..
	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllProducts(){
		return new ResponseEntity<List<Product>>(adminService.viewProducts(),HttpStatus.FOUND);
	}
	
	//Search Products by products name..
	@GetMapping("/product/{m}")
	public ResponseEntity<List<Product>> searchProducts(@PathVariable("m") String m){
		return new ResponseEntity<List<Product>>(adminService.searchProducts(m),HttpStatus.FOUND);
	}
	
	//add product Images
	@PutMapping("/product/{pid}")
	public ResponseEntity<Product> addImgs(@Valid @RequestBody List<ProductImg> imgs,@PathVariable("pid") Integer pid){
		return new ResponseEntity<Product>(adminService.addProductImg(pid, imgs),HttpStatus.ACCEPTED);
	}
	
	//add category
	@PostMapping("/category")
	public ResponseEntity<Category> addCategory(@Valid @RequestBody Category cat){
		return new ResponseEntity<Category>(adminService.addCategory(cat),HttpStatus.ACCEPTED);
	}
	
	//add Promocode
	@PostMapping("/promocode")
	public ResponseEntity<Promocode> addPromocode(@Valid @RequestBody Promocode p){
		return new ResponseEntity<Promocode>(adminService.addPromocode(p),HttpStatus.ACCEPTED);
	}
	
	//view Promocodes
	@GetMapping("/promocode")
	public ResponseEntity<List<Promocode>> getAllPromocodes(){
		return new ResponseEntity<List<Promocode>>(adminService.viewPromocodes(),HttpStatus.FOUND);
	}
}
