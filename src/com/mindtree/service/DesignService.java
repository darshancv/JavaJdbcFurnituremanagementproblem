package com.mindtree.service;

import java.util.List;
import java.util.Set;

import com.mindtree.entity.Design;
import com.mindtree.exception.ServiceException;

public interface DesignService {

	public Set<Design> insertDesigns(Set<Design> designs) throws ServiceException;

	public Set<Design> getDesignsByCompanyID(byte id) throws ServiceException;

	public String updateDesignRatingByID(byte id, double rating) throws ServiceException;

	public List<Design> ListAllDetailsUsingRating(double rating) throws ServiceException;

	public List<Design> getAllDesigns() throws ServiceException;

}
