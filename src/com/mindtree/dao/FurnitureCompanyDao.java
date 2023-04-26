package com.mindtree.dao;

import com.mindtree.entity.FurnitureCompany;
import com.mindtree.exception.DaoException;

public interface FurnitureCompanyDao {
	public FurnitureCompany getFurnitureCompanyByID(byte id) throws DaoException;

}
