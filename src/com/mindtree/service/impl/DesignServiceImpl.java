package com.mindtree.service.impl;

import java.util.List;
import java.util.Set;

import com.mindtree.dao.DesignDao;
import com.mindtree.dao.impl.DesignDaoImpl;
import com.mindtree.entity.Design;
import com.mindtree.exception.DaoException;
import com.mindtree.exception.ServiceException;
import com.mindtree.exception.impl.CompmanyIdNotFoundException;
import com.mindtree.exception.impl.DesignIdNotFoundException;
import com.mindtree.service.DesignService;

public class DesignServiceImpl implements DesignService {
	private static DesignDao dao = new DesignDaoImpl();

	@Override
	public Set<Design> insertDesigns(Set<Design> designs) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			return dao.saveDesignsIntoDb(designs);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new ServiceException("Service:Insertion not happended please check with the values");
		}
	}

	@Override
	public Set<Design> getDesignsByCompanyID(byte id) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			Set<Design> designs = dao.getDesignsByCompanyIDFromDb(id);
			if (designs.size() > 0) {
				return designs;
			} else {
				throw new CompmanyIdNotFoundException("Service: Company id is wrong please check once");
			}

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new ServiceException("Service: Company id is wrong please check once");
		}

	}

	@Override
	public String updateDesignRatingByID(byte id, double rating) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			return dao.updateDesignRatingInDb(id, rating);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new DesignIdNotFoundException("Service: Design id is incorrect please check once");
		}
	}

	@Override
	public List<Design> ListAllDetailsUsingRating(double rating) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			return dao.listAllDesignsUsingRatingFromDb(rating);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new ServiceException("Service: Please provide the correct rating");
		}
	}

	@Override
	public List<Design> getAllDesigns() throws ServiceException {
		// TODO Auto-generated method stub
		try {
			return dao.getAllDesignsFromDb();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new ServiceException("Service: No data is presented in the Database");
		}
	}

}
