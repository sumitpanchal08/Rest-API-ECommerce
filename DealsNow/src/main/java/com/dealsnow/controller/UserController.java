package com.dealsnow.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dealsnow.models.Address;
import com.dealsnow.models.CartOrder;
import com.dealsnow.models.Category;
import com.dealsnow.models.CurrentSession;
import com.dealsnow.models.Product;
import com.dealsnow.models.ProductOrderDetails;
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
	public ResponseEntity<CurrentSession> loginUser(@Valid @RequestBody UserDTO userdto){
		return new ResponseEntity<>(userService.loginUser(userdto),HttpStatus.ACCEPTED);
	}
	@GetMapping("/login/details/{uuid}")
	public ResponseEntity<User> loginDetails(@PathVariable("uuid") String uuid){
		return new ResponseEntity<>(userService.getLoginDetails(uuid),HttpStatus.ACCEPTED);
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
	
	@GetMapping("/product/byBrand/{val}")
	public ResponseEntity<List<Product>> getByBrand(@PathVariable("val")String val){
		return new ResponseEntity<List<Product>>(productService.searchByBrand(val),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/price/{val}")
	public ResponseEntity<List<Product>> getPriceLessThan(@PathVariable("val")Double val){
		return new ResponseEntity<List<Product>>(productService.searchByPriceLessThan(val),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/address/{id}")
	public ResponseEntity<User> addAddress(@Valid @RequestBody Address address, @PathVariable("id")Integer userid){
		return new ResponseEntity<User>(userService.addAddress(address, userid),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/address/{id}")
	public ResponseEntity<Address> deleteAddress(@PathVariable("id") Integer id){
		return new ResponseEntity<Address>(userService.deleteAddress(id),HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/order/create/{userId}")
	public ResponseEntity<CartOrder> createOrder(@PathVariable("userId") Integer userId){
		return new ResponseEntity<CartOrder>(userService.createOrder(userId),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/order/addToCart/{oid}/{pid}")
	public ResponseEntity<CartOrder> addToCart(@PathVariable("oid")Integer oid,@PathVariable("pid")Integer pid, @RequestBody ProductOrderDetails pod){
		return new ResponseEntity<CartOrder>(userService.addToCart(pod,oid,pid),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/order/removeFromCart/{oid}/{podid}")
	public ResponseEntity<CartOrder> removeFromCart(@PathVariable("oid")Integer oid,@PathVariable("podid")Integer podid){
		return new ResponseEntity<CartOrder>(userService.removeFromCart(oid, podid),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/order/applyPromo/{oid}/{code}")
	public ResponseEntity<CartOrder> applyPromo(@PathVariable("code")String code,@PathVariable("oid")Integer oid){
		return new ResponseEntity<CartOrder>(userService.applyPromo(oid,code),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/order/removePromo/{oid}")
	public ResponseEntity<CartOrder> removePromo(@PathVariable("oid")Integer oid){
		return new ResponseEntity<CartOrder>(userService.removePromo(oid),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/order/addAddress/{oid}/{id}")
	public ResponseEntity<CartOrder> addAddress(@PathVariable("id")Integer pid,@PathVariable("oid")Integer oid){
		return new ResponseEntity<CartOrder>(userService.addAddress(oid,pid),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("order/confirm/{uid}/{oid}")
	public ResponseEntity<CartOrder> confirmOrder(@PathVariable("uid")Integer uid,@PathVariable("oid")Integer oid){
		return new ResponseEntity<CartOrder>(userService.confirmOrder(oid, uid),HttpStatus.ACCEPTED);
	}
	
	
}
