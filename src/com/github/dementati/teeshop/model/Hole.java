package com.github.dementati.teeshop.model;

import java.io.Serializable;

public class Hole implements Serializable {
	static final long serialVersionUID = 1L;
	
	private boolean fairwayHit;
	private float fairwayHitDist;
	private boolean greenHit;
	private float greenHitDist;
	private int puttCount;
	
	public Hole(boolean fairwayHit, 
				float fairwayHitDist, 
				boolean greenHit, 
				float greenHitDist, 
				int nPutts) {
		this.fairwayHit = fairwayHit;
		this.fairwayHitDist = fairwayHitDist;
		this.greenHit = greenHit;
		this.greenHitDist = greenHitDist;
		this.puttCount = nPutts;
	}
	
	public boolean isFairwayHit() {
		return fairwayHit;
	}
	
	public float getFairwayHitDist() {
		return fairwayHitDist;
	}
	
	public boolean isGreenHit() {
		return greenHit;
	}
	
	public float getGreenHitDist() {
		return greenHitDist;
	}
	
	public int getPuttCount() {
		return puttCount;
	}
}
