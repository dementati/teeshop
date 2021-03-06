package com.github.dementati.teeshop.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Round implements Serializable {
	static final long serialVersionUID = 1L;
	
	private Calendar date;
	private ArrayList<Hole> holes = new ArrayList<Hole>();
	
	public Round(Calendar date) {
		this.date = date;
		
		for(int i = 0; i < 18; i++) {
			holes.add(new Hole(null, null, null, null, null, null, null, null, null));
		}
	}
	
	public ArrayList<Hole> getHoles() {
		return holes;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public String getDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return sdf.format(date.getTime());
	}
	
	public double fairwayHitFreq() {
		int count = 0;
		for(Hole hole : holes) {
			if(hole.isFairwayHit()) {
				count++;
			}
		}
		return (double)count/(double)holes.size();
	}
	
	public double transportHitFreq() {
		int count = 0;
		for(Hole hole : holes) {
			if(hole.isTransportHit()) {
				count++;
			}
		}
		return (double)count/(double)holes.size();
	}
	
	public double greenHitFreq() {
		int count = 0;
		for(Hole hole : holes) {
			if(hole.isGreenHit()) {
				count++;
			}
		}
		return (double)count/(double)holes.size();
	}
}
