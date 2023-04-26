package com.mindtree.entity;

import java.io.Serializable;

public class FurnitureCompany implements Serializable{
	private byte id;
	private String name;

	public FurnitureCompany() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FurnitureCompany(byte id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
