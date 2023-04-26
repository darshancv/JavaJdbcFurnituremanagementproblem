package com.mindtree.service;

import com.mindtree.entity.FurnitureCompany;
import com.mindtree.exception.ServiceException;

public interface FurnitureComapanyService {

	public FurnitureCompany getFurnitureCompanyByID(byte id) throws ServiceException;

}
