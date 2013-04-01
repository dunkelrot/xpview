package com.basf.xpview.model.graphics;

import javax.vecmath.Point3d;

public class BoundingBox {

	protected double minX;
	protected double minY;
	protected double minZ;
	
	protected double maxX;
	protected double maxY;
	protected double maxZ;
	
	public BoundingBox() {
		
	}
	
	public void setNull() {
		maxX = 0;
		maxY = 0;
		minX = 0;
		minY = 0;
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

	public void add(double x, double y) {
		if (maxX < x) {
			maxX = x;
		}
		if (maxY < y) {
			maxY = y;
		}
		if (minX > x) {
			minX = x;
		}
		if (minY > y) {
			minY = y;
		}		
	}
	
	public void add(BoundingBox other) {
		add(other.maxX, other.maxY);
		add(other.minX, other.minY);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(maxX);
		buffer.append(":");
		buffer.append(maxY);
		buffer.append("] [");
		buffer.append(minX);
		buffer.append(":");
		buffer.append(minY);
		buffer.append("]");
		return buffer.toString();
	}
	
	public Point3d getCenter() {
		Point3d pt = new Point3d((maxX - minX) * 0.5, (maxY - minY) * 0.5, 0.0);
		return pt;
	}
	
	public double getWidth() {
		return Math.abs(maxX - minX);
	}
	
	public double getHeight() {
		return Math.abs(maxY - minY);
	}
}
