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
import com.dealsnow.models.User;
import com.dealsnow.service.AdminService;
import com.dealsnow.service.CategoryService;
import com.dealsnow.service.ProductService;
import com.dealsnow.service.PromocodeService;
import com.dealsnow.service.UserService;

@RestController
@RequestMapping("/Admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PromocodeService promocodeService;
	
	@Autowired
	private UserService userService;
	
	//register admin
	@PostMapping("/register")
	public ResponseEntity<Admin> registerAdmin(@Valid @RequestBody Admin admin){
		return new ResponseEntity<>(adminService.registerAdmin(admin),HttpStatus.ACCEPTED);
	}
	
	//login admin
	@PostMapping("/login")
	public ResponseEntity<String> loginAdmin(@Valid @RequestBody AdminDTO adminDTO){
		return new ResponseEntity<>(adminService.loginAdmin(adminDTO),HttpStatus.ACCEPTED);
	}
	
	//logout admin
	@DeleteMapping("/logout/{uuid}")
	public ResponseEntity<String> logoutAdmin(@Valid @PathVariable("uuid") String uuid){
		return new ResponseEntity<String>(adminService.logoutAdmin(uuid),HttpStatus.ACCEPTED);
	}
	
	//add new product
	@PostMapping("/product/{uuid}/{catId}")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product p,@PathVariable("catId") Integer catId,@PathVariable("uuid") String uuid){
		adminService.checkLoginStatus(uuid);
		return new ResponseEntity<Product>(productService.addProduct(p, catId),HttpStatus.ACCEPTED);
	}
	
	//get all Products available in database..
	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllProducts(){
		return new ResponseEntity<List<Product>>(productService.viewProducts(),HttpStatus.ACCEPTED);
	}
	
	//Search Products by products name..
	@GetMapping("/product/{m}")
	public ResponseEntity<List<Product>> searchProducts(@PathVariable("m") String m){
		return new ResponseEntity<List<Product>>(productService.searchProducts(m),HttpStatus.ACCEPTED);
	}
	
	//add product Images
	@PutMapping("/product/{uuid}/{pid}")
	public ResponseEntity<Product> addImgs(@Valid @RequestBody List<ProductImg> imgs,@PathVariable("pid") Integer pid,@PathVariable("uuid") String uuid){
		adminService.checkLoginStatus(uuid);
		return new ResponseEntity<Product>(productService.addProductImg(pid, imgs),HttpStatus.ACCEPTED);
	}
	
	//add category
	@PostMapping("/category/{uuid}")
	public ResponseEntity<Category> addCategory(@Valid @RequestBody Category cat,@PathVariable("uuid") String uuid){
		adminService.checkLoginStatus(uuid);
		return new ResponseEntity<Category>(categoryService.addCategory(cat),HttpStatus.ACCEPTED);
	}
	
	//add Promocode
	@PostMapping("/promocode/{uuid}")
	public ResponseEntity<Promocode> addPromocode(@Valid @RequestBody Promocode p,@PathVariable("uuid") String uuid){
		adminService.checkLoginStatus(uuid);
		return new ResponseEntity<Promocode>(promocodeService.addPromocode(p),HttpStatus.ACCEPTED);
	}
	
	//view Promocodes
	@GetMapping("/promocode")
	public ResponseEntity<List<Promocode>> getAllPromocodes(){
		return new ResponseEntity<List<Promocode>>(promocodeService.viewPromocodes(),HttpStatus.ACCEPTED);
	}
	
	//view Users
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(){
		return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.ACCEPTED);
	}
}
