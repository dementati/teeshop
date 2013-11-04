package com.github.dementati.teeshop.model;

import java.util.ArrayList;
import java.util.Calendar;

public class Round {
	private Calendar date;
	private ArrayList<Hole> holes = new ArrayList<Hole>();
	
	public Round(Calendar date) {
		this.date = date;
	}
	
	public ArrayList<Hole> getHoles() {
		return holes;
	}
	
	public void addHole(Hole hole) {
		if(holes.size() >= 18) {
			throw new IllegalAccessError("One round cannot have more than 18 holes.");
		}
		
		holes.add(hole);
	}
	
	public Calendar getDate() {
		return date;
	}
}
