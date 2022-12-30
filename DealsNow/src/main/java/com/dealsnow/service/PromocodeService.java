package com.dealsnow.service;

import java.util.List;

import com.dealsnow.exceptions.PromocodeException;
import com.dealsnow.models.Promocode;

public interface PromocodeService {
	
    public Promocode addPromocode(Promocode promocode)throws PromocodeException;
	
	public List<Promocode> viewPromocodes() throws PromocodeException;

}
