package com.github.dementati.teeshop.model;

import java.io.Serializable;

public class Hole implements Serializable {
	static final long serialVersionUID = 1L;
	
	private Boolean fairwayHit;
	private Float fairwayHitDist;
	private Boolean transportHit;
	private Float transportHitDist;
	private Boolean greenHit;
	private Float greenHitDist;
	private Integer puttCount;
	private Integer par;
	private Float holeDistance;
	
	public Hole(Boolean fairwayHit, 
				Float fairwayHitDist, 
				Boolean transportHit,
				Float transportHitDist,
				Boolean greenHit, 
				Float greenHitDist, 
				Integer puttCount,
				Integer par,
				Float holeDistance) {
		if(fairwayHit == null) {
			this.fairwayHit = Boolean.valueOf(false);
		} else {
			this.fairwayHit = fairwayHit;
		}
		
		this.fairwayHitDist = fairwayHitDist;
		
		if(transportHit == null) {
			this.transportHit = Boolean.valueOf(false);
		} else {
			this.transportHit = transportHit;
		}
		
		this.transportHitDist = transportHitDist;
		
		if(greenHit == null) {
			this.greenHit = Boolean.valueOf(false);
		} else {
			this.greenHit = greenHit;
		}
		
		this.greenHitDist = greenHitDist;
		this.puttCount = puttCount;
		this.par = par;
		this.holeDistance = holeDistance;
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
		
		if(this.transportHit != null && !this.transportHit.equals(other.transportHit)) {
			return false;
		} else if(this.transportHit == null && other.transportHit != null) {
			return false;
		}
		
		if(this.transportHitDist != null && !this.transportHitDist.equals(other.transportHitDist)) {
			return false;
		} else if(this.transportHitDist == null && other.transportHitDist != null) {
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
		
		if(this.par != null && !this.par.equals(other.par)) {
			return false;
		} else if(this.par == null && other.par != null) {
			return false;
		}
		
		if(this.holeDistance != null && !this.holeDistance.equals(other.holeDistance)) {
			return false;
		} else if(this.holeDistance == null && other.holeDistance != null) {
			return false;
		}
		
		return equal;
	}
	
	@Override
	public String toString() {
		return "Hole(" + fairwayHit + ", " + fairwayHitDist +", " + transportHit + ", " + transportHitDist + ", " + greenHit + ", " + greenHitDist + ", " + puttCount + ", " + par + ", " + holeDistance + ")";
	}
	
	public Boolean isFairwayHit() {
		return fairwayHit;
	}
	
	public Float getFairwayHitDist() {
		return fairwayHitDist;
	}
	
	public Boolean isTransportHit() {
		return transportHit;
	}
	
	public Float getTransportHitDist() {
		return transportHitDist;
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
	
	public Integer getPar() {
		return par;
	}
	
	public Float getHoleDistance() {
		return holeDistance;
	}
}
