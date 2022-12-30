package com.dealsnow.service;

import java.util.List;

import com.dealsnow.exceptions.AdminException;
import com.dealsnow.exceptions.CategoryException;
import com.dealsnow.exceptions.ProductException;
import com.dealsnow.exceptions.PromocodeException;
import com.dealsnow.models.Admin;
import com.dealsnow.models.AdminDTO;
import com.dealsnow.models.Category;
import com.dealsnow.models.Product;
import com.dealsnow.models.ProductImg;
import com.dealsnow.models.Promocode;

public interface AdminService {
	public Admin registerAdmin(Admin admin)throws AdminException;
	public String loginAdmin(AdminDTO adminDTO)throws AdminException;
	public String logoutAdmin(String uuid)throws AdminException;
	public Boolean checkLoginStatus(String uuid)throws AdminException;
	
}
