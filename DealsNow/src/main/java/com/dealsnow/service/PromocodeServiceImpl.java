package com.dealsnow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsnow.dao.PromocodeDAO;
import com.dealsnow.exceptions.ProductException;
import com.dealsnow.exceptions.PromocodeException;
import com.dealsnow.models.Promocode;

@Service
public class PromocodeServiceImpl implements PromocodeService{
	
	@Autowired
	private PromocodeDAO promocodedao;
	
	@Override
	public Promocode addPromocode(Promocode promocode) throws PromocodeException {
		// TODO Auto-generated method stub
		Promocode promocode2=promocodedao.save(promocode);
		if(promocode2==null) {
			throw new PromocodeException("Promocode not Added!! Failed...");
		}
		return promocode2;
	}

	

	@Override
	public List<Promocode> viewPromocodes() throws PromocodeException {
		// TODO Auto-generated method stub
		List<Promocode> promocodes=promocodedao.findAll();
		if(promocodes.size()==0) {
			throw new ProductException("No Promocode found!!!");
		}
		return promocodes;
	}
}
