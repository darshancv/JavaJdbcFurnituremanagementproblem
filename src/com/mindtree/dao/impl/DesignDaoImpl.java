package com.mindtree.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.mindtree.dao.DesignDao;
import com.mindtree.entity.Design;
import com.mindtree.entity.FurnitureCompany;
import com.mindtree.exception.DaoException;
import com.mindtree.utility.DBConnection;

public class DesignDaoImpl implements DesignDao {

	@Override
	public Set<Design> saveDesignsIntoDb(Set<Design> designs) throws DaoException {
		// TODO Auto-generated method stub
		Set<Design> set = new LinkedHashSet<>();
		String query = "insert into design(name,design_pattern,rating,fid) values(?,?,?,?)";
		for (Design design : designs) {

			Connection connection = DBConnection.connection();
			PreparedStatement statement = null;
			String name = design.getName();
			String pattern = design.getDesignPattern();
			double rating = design.getRating();
			byte fid = design.getFurniture().getId();
			try {
				statement = connection.prepareStatement(query);
				statement.setString(1, name);
				statement.setString(2, pattern);
				statement.setDouble(3, rating);
				statement.setByte(4, fid);
				int count = statement.executeUpdate();
				if (count == 1) {
					System.out.println(count + "row updated");
					set.add(design);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new DaoException("Dao:Insertion not done please check your details");
			} finally {
				try {
					statement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return set;
	}

	@Override
	public Set<Design> getDesignsByCompanyIDFromDb(byte id) throws DaoException {
		// TODO Auto-generated method stub
		Set<Design> sets = new LinkedHashSet<>();
		String query = "select d.* from funrniture_company f,design d where f.id=d.fid and f.id=" + id;
		Connection connection = DBConnection.connection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				Design design = new Design(set.getString(2), set.getString(3), set.getDouble(4));
				sets.add(design);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DaoException("Dao:please check the company id");
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sets;

	}

	@Override
	public String updateDesignRatingInDb(byte id, double rating) throws DaoException {
		// TODO Auto-generated method stub
		String string = "";
		String query = "update design set rating=" + rating + " where id=" + id;
		Connection connection = DBConnection.connection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
			int count = statement.executeUpdate();
			if (count == 1) {
				string = count + "row updated";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DaoException("Dao:Please check the design Id");
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return string;
	}

	@Override
	public List<Design> listAllDesignsUsingRatingFromDb(double rating) throws DaoException {
		// TODO Auto-generated method stub
		List<Design> list = new ArrayList<>();
		String query = "select d.* ,f.* from funrniture_company f RIGHT JOIN design d on f.id=d.fid where d.rating>"
				+ rating;
		Connection connection = DBConnection.connection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				FurnitureCompany company = new FurnitureCompany(set.getByte(6), set.getString(7));
				Design design = new Design(set.getString(1), set.getString(2), set.getDouble(3), company);
				list.add(design);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DaoException("Dao:Please give the rating properly");
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public List<Design> getAllDesignsFromDb() throws DaoException {
		// TODO Auto-generated method stub

		List<Design> list = new ArrayList<>();
		String query = "{call displayDesigns()} ";
		Connection connection = DBConnection.connection();
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall(query);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				Design design = new Design(set.getString(1), set.getString(2), set.getDouble(3));
				list.add(design);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DaoException("Dao:Data not present in the database");
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

}
