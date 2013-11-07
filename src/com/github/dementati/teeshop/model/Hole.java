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
	
	@Override
	public boolean equals(Object o) {
		Hole other = (Hole)o;
		
		boolean equal = true;
		
		if(this.fairwayHit != null && !this.fairwayHit.equals(other.fairwayHit)) {
			return false;
		} else if(this.fairwayHit == null && other.fairwayHit != null) {
			return false;
		}
		
		if(this.fairwayHitDist != null && !this.fairwayHitDist.equals(other.fairwayHitDist)) {
			return false;
		} else if(this.fairwayHitDist == null && other.fairwayHitDist != null) {
			return false;
		}
		
		if(this.greenHit != null && !this.greenHit.equals(other.greenHit)) {
			return false;
		} else if(this.greenHit == null && other.greenHit != null) {
			return false;
		}
		
		if(this.greenHitDist != null && !this.greenHitDist.equals(other.greenHitDist)) {
			return false;
		} else if(this.greenHitDist == null && other.greenHitDist != null) {
			return false;
		}
		
		if(this.puttCount != null && !this.puttCount.equals(other.puttCount)) {
			return false;
		} else if(this.puttCount == null && other.puttCount != null) {
			return false;
		}
		
		return equal;
	}
	
	@Override
	public String toString() {
		return "Hole(" + fairwayHit + ", " + fairwayHitDist +", " + greenHit + ", " + greenHitDist + ", " + puttCount + ")";
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
