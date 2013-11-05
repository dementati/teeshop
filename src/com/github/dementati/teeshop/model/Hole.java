package com.github.dementati.teeshop.model;

import java.io.Serializable;

public class Hole implements Serializable {
	static final long serialVersionUID = 1L;
	
	private Boolean fairwayHit;
	private Float fairwayHitDist;
	private Boolean greenHit;
	private Float greenHitDist;
	private Integer puttCount;
	
	public Hole(Boolean fairwayHit, 
				Float fairwayHitDist, 
				Boolean greenHit, 
				Float greenHitDist, 
				Integer puttCount) {
		if(fairwayHit == null) {
			this.fairwayHit = Boolean.valueOf(false);
		} else {
			this.fairwayHit = fairwayHit;
		}
		
		this.fairwayHitDist = fairwayHitDist;
		
		if(greenHit == null) {
			this.greenHit = Boolean.valueOf(false);
		} else {
			this.greenHit = greenHit;
		}
		
		this.greenHitDist = greenHitDist;
		this.puttCount = puttCount;
	}
	
	public Boolean isFairwayHit() {
		return fairwayHit;
	}
	
	public Float getFairwayHitDist() {
		return fairwayHitDist;
	}
	
	public Boolean isGreenHit() {
		return greenHit;
	}
	
	public Float getGreenHitDist() {
		return greenHitDist;
	}
	
	public Integer getPuttCount() {
		return puttCount;
	}
}
