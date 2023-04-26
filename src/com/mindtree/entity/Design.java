package com.mindtree.entity;

import java.io.Serializable;

public class Design implements Serializable{
	private String name;
	private String designPattern;
	private double rating;
	private FurnitureCompany furniture;

	public Design() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Design(String name, String designPattern, double rating) {
		super();
		this.name = name;
		this.designPattern = designPattern;
		this.rating = rating;
	}

	public Design(String name, String designPattern, double rating, FurnitureCompany furniture) {
		super();
		this.name = name;
		this.designPattern = designPattern;
		this.rating = rating;
		this.furniture = furniture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignPattern() {
		return designPattern;
	}

	public void setDesignPattern(String designPattern) {
		this.designPattern = designPattern;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public FurnitureCompany getFurniture() {
		return furniture;
	}

	public void setFurniture(FurnitureCompany furniture) {
		this.furniture = furniture;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Design other = (Design) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
