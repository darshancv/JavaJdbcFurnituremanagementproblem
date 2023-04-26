package com.mindtree.dao;

import java.util.List;
import java.util.Set;

import com.mindtree.entity.Design;
import com.mindtree.exception.DaoException;

public interface DesignDao {
	public Set<Design> saveDesignsIntoDb(Set<Design> designs) throws DaoException;

	public Set<Design> getDesignsByCompanyIDFromDb(byte id) throws DaoException;

	public String updateDesignRatingInDb(byte id, double rating) throws DaoException;

	public List<Design> listAllDesignsUsingRatingFromDb(double rating) throws DaoException;

	public List<Design> getAllDesignsFromDb() throws DaoException;

}
