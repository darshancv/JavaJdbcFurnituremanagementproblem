package com.mindtree.entity;

import java.util.Comparator;

public class PatternNameCompare implements Comparator<Design>  {
	public int compare(Design m1, Design m2) {
		return m1.getDesignPattern().compareTo(m2.getDesignPattern());
	}
}

