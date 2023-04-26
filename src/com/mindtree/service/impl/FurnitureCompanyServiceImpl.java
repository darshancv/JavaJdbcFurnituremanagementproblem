package com.mindtree.service.impl;

import com.mindtree.dao.FurnitureCompanyDao;
import com.mindtree.dao.impl.FurnitureCompanyDaoImpl;
import com.mindtree.entity.FurnitureCompany;
import com.mindtree.exception.DaoException;
import com.mindtree.exception.ServiceException;
import com.mindtree.exception.impl.CompmanyIdNotFoundException;
import com.mindtree.service.FurnitureComapanyService;

public class FurnitureCompanyServiceImpl implements FurnitureComapanyService {
	private static FurnitureCompanyDao dao = new FurnitureCompanyDaoImpl();

	@Override
	public FurnitureCompany getFurnitureCompanyByID(byte id) throws ServiceException {
		// TODO Auto-generated method stub

		try {
			FurnitureCompany company = dao.getFurnitureCompanyByID(id);
			if (company != null) {
				return company;
			}
			else
			{
				throw new CompmanyIdNotFoundException("Service: Company id not found");
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new ServiceException("Service: Please check your id what you have entered");
		}
	}

}
