package com.mindtree.entity;

import java.util.Comparator;

public class NameCompare implements Comparator<Design> {
	public int compare(Design m1, Design m2) {
		return m1.getName().compareTo(m2.getName());
	}
}
