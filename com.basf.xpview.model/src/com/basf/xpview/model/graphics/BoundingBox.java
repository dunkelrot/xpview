package com.basf.xpview.model.graphics;

public class BoundingBox {

	protected double minX;
	protected double minY;
	protected double minZ;
	
	protected double maxX;
	protected double maxY;
	protected double maxZ;
	
	public BoundingBox() {
		
	}
	
	public BoundingBox(BoundingBox other) {
		this.maxX = other.maxX;
		this.maxY = other.maxY;
		this.maxZ = other.maxZ;
		this.minX = other.minX;
		this.minY = other.minY;
		this.minZ = other.minZ;
	}

	public BoundingBox(double minX, double minY, double maxX, double maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = 0;
		this.minX = minX;
		this.minY = minY;
		this.minZ = 0;
	}
	
	public double getMaxX() {
		return maxX;
	}
	
	public double getMaxY() {
		return maxY;
	}
	
	public double getMaxZ() {
		return maxZ;
	}
	
	public double getMinX() {
		return minX;
	}

	public double getMinY() {
		return minY;
	}
	
	public double getMinZ() {
		return minZ;
	}

}
