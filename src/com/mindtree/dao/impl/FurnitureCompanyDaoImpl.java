package com.mindtree.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mindtree.dao.FurnitureCompanyDao;
import com.mindtree.entity.FurnitureCompany;
import com.mindtree.exception.DaoException;
import com.mindtree.utility.DBConnection;

public class FurnitureCompanyDaoImpl implements FurnitureCompanyDao{

	@Override
	public FurnitureCompany getFurnitureCompanyByID(byte id) throws DaoException{
		// TODO Auto-generated method stub
		FurnitureCompany company=null;
		String query="select * from funrniture_company where id="+id;
		Connection connection=DBConnection.connection();
		PreparedStatement statement =null;
		try {
			statement = connection.prepareStatement(query);
			ResultSet set=statement.executeQuery();
			set.next();
			company=new FurnitureCompany(set.getByte(1),set.getString(2));
			return company;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DaoException("Dao: Please check the company Id what you have entered");
		}
		finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
