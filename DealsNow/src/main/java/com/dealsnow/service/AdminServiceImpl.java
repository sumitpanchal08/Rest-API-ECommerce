package com.dealsnow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsnow.dao.AdminDAO;
import com.dealsnow.dao.CategoryDAO;
import com.dealsnow.dao.CurrentSessionDAO;
import com.dealsnow.dao.ProductDAO;
import com.dealsnow.dao.PromocodeDAO;
import com.dealsnow.exceptions.AdminException;
import com.dealsnow.exceptions.CategoryException;
import com.dealsnow.exceptions.ProductException;
import com.dealsnow.exceptions.PromocodeException;
import com.dealsnow.exceptions.UserException;
import com.dealsnow.models.Admin;
import com.dealsnow.models.AdminDTO;
import com.dealsnow.models.Category;
import com.dealsnow.models.CurrentSession;
import com.dealsnow.models.Product;
import com.dealsnow.models.ProductImg;
import com.dealsnow.models.Promocode;

import net.bytebuddy.utility.RandomString;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDAO admindao;

	@Autowired
	private CurrentSessionDAO csdao;
	
	
	@Override
	public Admin registerAdmin(Admin admin) throws AdminException {
		Admin adminByUsername=admindao.findByUsername(admin.getUsername());
		if(adminByUsername!=null) {
			throw new AdminException("Username already registered!!");
		}
		Admin admin2=admindao.save(admin);
		if(admin2==null) {
			throw new AdminException("Admin register Failed!!");
		}
		return admin2;
	}

	@Override
	public String loginAdmin(AdminDTO adminDTO) throws AdminException {
		Admin admin=admindao.loginAdmin(adminDTO.getUsername(),adminDTO.getPassword());
		if (admin==null) {
			throw new AdminException("Admin username and password not match!!");
		}
		Optional<CurrentSession> currentSession=csdao.findById(admin.getAdminId());
		if(currentSession.isPresent()) {
			throw new AdminException("Admin already login!!");
		}
		String key=RandomString.make(6);
		CurrentSession cs1=csdao.findByUuid(key);
		if(cs1!=null) {
			throw new AdminException("Login Failed try Again!!");
		}
		CurrentSession cs=new CurrentSession(admin.getAdminId(),key,LocalDateTime.now(),true);
		csdao.save(cs);
		return cs.getUuid();
	}
	@Override
	public String logoutAdmin(String uuid) throws AdminException {
        CurrentSession cs=csdao.findByUuid(uuid);
		if(cs==null) {
			throw new AdminException("Admin Not Logged In with this number");
		}
		csdao.delete(cs);
		return "Logout Successfull!!";
	}

	@Override
	public Boolean checkLoginStatus(String uuid) throws AdminException {
		// TODO Auto-generated method stub
		CurrentSession cs=csdao.findByUuid(uuid);
		if(cs==null) {
			throw new AdminException("Admin Not Logged In with this number");
		}
		if(cs.getType()!=true) {
			throw new AdminException("Admin Not Logged In with this number");
		}
		return true;
	}

	
	
	
}
