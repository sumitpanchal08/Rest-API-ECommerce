package com.dealsnow.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dealsnow.models.Category;
import com.dealsnow.models.Product;
import com.dealsnow.models.User;
import com.dealsnow.models.UserDTO;
import com.dealsnow.service.CategoryService;
import com.dealsnow.service.ProductService;
import com.dealsnow.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@Valid @RequestBody User user){
		return new ResponseEntity<>(userService.registerUser(user),HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@Valid @RequestBody UserDTO userdto){
		return new ResponseEntity<String>(userService.loginUser(userdto),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/logout/{uuid}")
	public ResponseEntity<String> logoutUser(@PathVariable("uuid") String uuid){
		return new ResponseEntity<String>(userService.logoutUser(uuid),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllProducts(){
		return new ResponseEntity<List<Product>>(productService.viewProducts(),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/{searchValue}")
	public ResponseEntity<List<Product>> searchProducts(@PathVariable("searchValue") String m){
		return new ResponseEntity<List<Product>>(productService.searchProducts(m),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/category")
	public ResponseEntity<List<Category>> getAllCategory(){
		return new ResponseEntity<List<Category>>(categoryService.viewCategories(),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/bycategory/{id}")
	public ResponseEntity<List<Product>> getByCategory(@PathVariable("id")Integer catid){
		return new ResponseEntity<List<Product>>(productService.searchByCategory(catid),HttpStatus.ACCEPTED);
	}
	
	
	
	
	
	
	
	
	
}
