package com.dealsnow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsnow.dao.AddressDAO;
import com.dealsnow.dao.CartOrderDAO;
import com.dealsnow.dao.CurrentSessionDAO;
import com.dealsnow.dao.ProductDAO;
import com.dealsnow.dao.ProductOrderDetailsDAO;
import com.dealsnow.dao.PromocodeDAO;
import com.dealsnow.dao.UserDAO;
import com.dealsnow.exceptions.AddressException;
import com.dealsnow.exceptions.AdminException;
import com.dealsnow.exceptions.CartOrderException;
import com.dealsnow.exceptions.PromocodeException;
import com.dealsnow.exceptions.UserException;
import com.dealsnow.models.Address;
import com.dealsnow.models.CartOrder;
import com.dealsnow.models.CurrentSession;
import com.dealsnow.models.Product;
import com.dealsnow.models.ProductOrderDetails;
import com.dealsnow.models.Promocode;
import com.dealsnow.models.User;
import com.dealsnow.models.UserDTO;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userdao;
	
	@Autowired
	private CurrentSessionDAO csdao;
	
	@Autowired
	private CartOrderDAO cartOrderDAO;
	
	@Autowired
	private AddressDAO addressDao;
	
	@Autowired
	private PromocodeDAO promocodeDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ProductOrderDetailsDAO poddao;
	

	@Override
	public User registerUser(User user) throws UserException {
		// TODO Auto-generated method stub
		User u=userdao.findByMobile(user.getMobile());
		if(u!=null) {
			throw new UserException("Mobile already registered try using new mobile!!!");
		}
	    user.setLastmodified(LocalDateTime.now());
	    user.setRegisterDateTime(LocalDateTime.now());
	    User user2=userdao.save(user);
		return user2;
	}

	@Override
	public CurrentSession loginUser(UserDTO userDTO) throws UserException {
		// TODO Auto-generated method stub
		User user=userdao.loginUser(userDTO.getMobile(), userDTO.getPassword());
		if(user==null) {
			throw new UserException("Mobile and Password not match!!");
		}
		Optional<CurrentSession> cs=csdao.findById(user.getUserId());
		if(cs.isPresent()) {
			throw new UserException("User already login!!");
		}
        String key=RandomString.make(6);
        CurrentSession cs1=csdao.findByUuid(key);
		if(cs1!=null) {
			throw new UserException("Login Failed try Again!!");
		}
		CurrentSession cs2=new CurrentSession(user.getUserId(),key,LocalDateTime.now(),false);
		csdao.save(cs2);
		return cs2;
	}

	@Override
	public String logoutUser(String uuid) throws UserException {
		CurrentSession cs=csdao.findByUuid(uuid);
		if(cs==null) {
			throw new UserException("User not login with this id");
		}
		csdao.delete(cs);
		return "Logout Successfull!!";
	}


	@Override
	public Boolean checkLoginStatus(String uuid) throws AdminException {
		// TODO Auto-generated method stub
		CurrentSession cs=csdao.findByUuid(uuid);
		if(cs==null) {
			throw new AdminException("User Not Logged In with this number");
		}
		if(cs.getType()!=false) {
			throw new AdminException("User Not Logged In with this number");
		}
		return true;
	}

	@Override
	public User getLoginDetails(String uuid) throws UserException {
		// TODO Auto-generated method stub
		CurrentSession cs=csdao.findByUuid(uuid);
		if(cs==null) {
			throw new UserException("User Not Login!!0");
		}
		if(cs.getType()) {
			throw new UserException("User Login Failed!!1");
		}
		
		Optional<User> optional= userdao.findById(cs.getUserId());
		if(!optional.isPresent()) {
			throw new UserException("User Login Failed!!2");
		}
		
		return optional.get();
	}

	
	@Override
	public User addAddress(Address address, Integer userId) throws AddressException, UserException {
		// TODO Auto-generated method stub
		Optional<User> optional=userdao.findById(userId);
		if(!optional.isPresent()) {
			throw new UserException("User Id is not Correct");
		}
		User user=optional.get();
		Set<Address> addresses=user.getAddresses();
		addresses.add(address);
		user.setAddresses(addresses);
		User user2=userdao.save(user);
		return user2;
	}

	@Override
	public Address deleteAddress(Integer id) throws AddressException {
		// TODO Auto-generated method stub
		Optional<Address> address=addressDao.findById(id);
		if(!address.isPresent()) {
			throw new AddressException("Address is not there");
		}
		addressDao.delete(address.get());
		return address.get();
	}
	
	@Override
	public CartOrder addToCart(ProductOrderDetails productOrderDetails,Integer oid,Integer pid) throws CartOrderException {
		// TODO Auto-generated method stub
		Optional<Product> optional1=productDAO.findById(pid);
		if(!optional1.isPresent()) {
			throw new CartOrderException("Product Id is not correct");
		}
		Product product=optional1.get();
		Optional<CartOrder> optional=cartOrderDAO.findById(oid);
		if(!optional.isPresent()) {
			throw new CartOrderException("Order Id is not correct");
		}
		CartOrder order=optional.get();
		if(order.getOrderStatus().equals("CONFIRMED")) {
			throw new CartOrderException("Order already Confirmed!!");
		}
		Set<ProductOrderDetails> pods= order.getProductOrderDetails();
		boolean flag=true;
		for(ProductOrderDetails d: pods) {
			if(d.getProduct().getProductId()==pid) {
				int count=d.getQuantity();
				flag=false;
				count++;
				d.setQuantity(count);
			}
		}
		if(flag) {
			productOrderDetails.setProduct(product);
			pods.add(productOrderDetails);
		}else {
			
		}
		order.setProductOrderDetails(pods);
		Double total=0.0;
		Set<ProductOrderDetails> details=order.getProductOrderDetails();
		order.setProductOrderDetails(details);
		for(ProductOrderDetails d:details) {
			total=total+(d.getProduct().getSellPrice()*d.getQuantity());
		}
		
		order.setTotalamount(total);
		CartOrder order2= cartOrderDAO.save(order);
		return order2;
	}

	@Override
	public CartOrder applyPromo(Integer oid,String code) throws PromocodeException {
		// TODO Auto-generated method stub
		Optional<CartOrder> optional1=cartOrderDAO.findById(oid);
		if(optional1==null) {
			throw new CartOrderException("Order is not valid");
		}
		CartOrder order=optional1.get();
		if(order.getOrderStatus().equals("CONFIRMED")) {
			throw new CartOrderException("Order is Already Confirmed");
		}
		Optional<Promocode> optional=promocodeDAO.findByCode(code);
		if(!optional.isPresent()) {
			throw new UserException("Promocode is Not Valid!!");
		}
		Promocode promocode=order.getPromocode();
		if(promocode!=null) {
			throw new UserException("Promocode is Already Applied!!");
		}
		Promocode promo=optional.get();
		order.setPromocode(promo);
		if(promo.getAmt()>order.getTotalamount()) {
			throw new UserException("Promocode cannot Apply here!!");
		}
		order.setTotalamount(order.getTotalamount()-promo.getAmt());
		CartOrder order2=cartOrderDAO.save(order);
		return order2;
	}

	@Override
	public CartOrder addAddress(Integer oid, Integer addressId) throws AddressException, CartOrderException {
		// TODO Auto-generated method stub
		Optional<CartOrder> optional=cartOrderDAO.findById(oid);
		if(optional==null) {
			throw new CartOrderException("Order is not valid");
		}
		CartOrder order=optional.get();
		if(order.getOrderStatus().equals("CONFIRMED")) {
			throw new CartOrderException("Order is Already Confirmed");
		}
		Optional<Address> optional1=addressDao.findById(addressId);
		if(!optional1.isPresent()) {
			throw new UserException("Address is Not Valid!!");
		}
		order.setAddress(optional1.get());
		CartOrder order2=cartOrderDAO.save(order);
		return order2;
	}

	@Override
	public CartOrder removePromo(Integer oid) throws CartOrderException {
		Optional<CartOrder> optional=cartOrderDAO.findById(oid);
		if(optional==null) {
			throw new CartOrderException("Order is not valid");
		}
		CartOrder order=optional.get();
		if(order.getOrderStatus().equals("CONFIRMED")) {
			throw new CartOrderException("Order already Confirmed!!");
		}
		Promocode promocode=order.getPromocode();
		Double double1=order.getTotalamount();
		order.setTotalamount(double1+promocode.getAmt());
		order.setPromocode(null);
		return cartOrderDAO.save(order);
	}

	@Override
	public CartOrder confirmOrder(Integer oid, Integer userId) throws UserException, CartOrderException {
		// TODO Auto-generated method stub
		Optional<CartOrder> optional1=cartOrderDAO.findById(oid);
		if(!optional1.isPresent()) {
			throw new CartOrderException("Order is not valid");
		}
		CartOrder order=optional1.get();
		if(order.getOrderStatus().equals("CONFIRMED")) {
			throw new CartOrderException("Order already Confirmed!!");
		}
		Address address=order.getAddress();
		if(address==null) {
		    throw new CartOrderException("Address Cannot be null!!");
		}
		Optional<User> optional=userdao.findById(userId);
		if(!optional.isPresent() || optional.get().getUserId()!=order.getUser().getUserId()) {
			throw new UserException("User Not Registered!!");
		}
		order.setOrderDateTime(LocalDateTime.now());
		order.setOrderStatus("CONFIRMED");
		Double total=0.0;
		Set<ProductOrderDetails> details=order.getProductOrderDetails();
		if(details.size()==0) {
			throw new CartOrderException("No product in Cart!!");
		}
		for(ProductOrderDetails d:details) {
			total=total+(d.getProduct().getSellPrice()*d.getQuantity());
		}
		Promocode promo=order.getPromocode();
		if(promo!=null) {
			if(promo.getAmt()>total) {
				throw new UserException("Promocode cannot Apply here!!");
			}
			total=total-promo.getAmt();
		}
		order.setTotalamount(total);
		CartOrder order2=cartOrderDAO.save(order);
		return order2;
	}

	@Override
	public CartOrder createOrder(Integer userId) throws UserException {
		Optional<User> optional=userdao.findById(userId);
		if(!optional.isPresent()) {
			throw new UserException("User Not Registered!!");
		}
		// TODO Auto-generated method stub
		List<CartOrder> orders=cartOrderDAO.findByUser(optional.get());
		for(CartOrder o:orders) {
			if(o.getOrderStatus().equals("PENDING")) {
				return o;
			}
		}
		CartOrder order=new CartOrder();
		order.setUser(optional.get());
		order.setOrderStatus("PENDING");
		CartOrder order2=cartOrderDAO.save(order);
		return order2;
	}

	@Override
	public CartOrder removeFromCart(Integer oid, Integer podid) throws CartOrderException {
		// TODO Auto-generated method stub
		Optional<CartOrder> optional=cartOrderDAO.findById(oid);
		if(!optional.isPresent()) {
			throw new CartOrderException("Order is not Valid");
		}
		CartOrder order=optional.get();
		Set<ProductOrderDetails> details=order.getProductOrderDetails();
		Double total=0.0;
		for(ProductOrderDetails p:details) {
			if(p.getProductOrderDetailId()==podid) {
				details.remove(p);
				Optional<ProductOrderDetails> pod=poddao.findById(podid);
				ProductOrderDetails podDetails=pod.get();
				podDetails.setProduct(null);
				poddao.save(podDetails);
				poddao.delete(podDetails);
			}else {
				total=total+(p.getProduct().getSellPrice()*p.getQuantity());
			}
		}
		Promocode promocode=order.getPromocode();
		if(promocode!=null) {
			if(promocode.getAmt()>total) {
				order.setPromocode(null);
			}else {
				total=total+promocode.getAmt();
			}
			
		}
		order.setProductOrderDetails(details);
		order.setTotalamount(total);
		return cartOrderDAO.save(order);
	}

	@Override
	public List<User> getAllUsers() throws UserException {
		// TODO Auto-generated method stub
		List<User> users=userdao.findAll();
		return users;
	}

}
