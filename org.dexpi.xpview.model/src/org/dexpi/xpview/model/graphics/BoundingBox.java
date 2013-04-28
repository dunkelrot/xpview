package org.dexpi.xpview.model.graphics;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class BoundingBox {

	protected double minX;
	protected double minY;
	protected double minZ;
	
	protected double maxX;
	protected double maxY;
	protected double maxZ;
	
	protected boolean empty;
	
	protected Rectangle2D rectangle;
	
	public BoundingBox() {
		this.empty = true;
	}
	
	public void clear() {
		maxX = 0;
		maxY = 0;
		minX = 0;
		minY = 0;
		this.empty = true;
	}
	
	public BoundingBox(BoundingBox other) {
		this.maxX = other.maxX;
		this.maxY = other.maxY;
		this.maxZ = other.maxZ;
		this.minX = other.minX;
		this.minY = other.minY;
		this.minZ = other.minZ;
		this.empty = other.empty;
	}

	public BoundingBox(double minX, double minY, double maxX, double maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = 0;
		this.minX = minX;
		this.minY = minY;
		this.minZ = 0;
		this.empty = false;
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
		if (empty == true) {
			maxX = x;
			minX = x;
			maxY = y;
			minY = y;
			empty = false;
		} else {
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
	}
	
	public void add(BoundingBox other) {
		if (other.isEmpty() == false) {
			add(other.maxX, other.maxY);
			add(other.minX, other.minY);
		}
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
		Point3d pt = new Point3d(minX + (maxX - minX) * 0.5, minY + (maxY - minY) * 0.5, 0.0);
		return pt;
	}
	
	public double getWidth() {
		return Math.abs(maxX - minX);
	}
	
	public double getHeight() {
		return Math.abs(maxY - minY);
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public void updateShape() {
		rectangle = new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
	}
	
	public void inflate(double value) {
		Point3d center = getCenter();
		Vector3d vec1 = new Vector3d(center.x - maxX, center.y - maxY, 0);
		vec1.scale(value);
		maxX = center.x + vec1.x;
		maxY = center.y + vec1.y;
		
		Vector3d vec2 = new Vector3d(center.x - minX, center.y - minY, 0);
		vec2.scale(value);
		minX = center.x + vec2.x;
		minY = center.y + vec2.y;		
	}
	
	public BoundingBox transformed(AffineTransform transform) {
		BoundingBox bBox = new BoundingBox();
		Point2D pt1 = new Point2D.Double(maxX, maxY);
		Point2D pt2 = new Point2D.Double(minX, minY);
		
		Point2D result = new Point2D.Double(0,0);
		transform.transform(pt1, result);
		bBox.add(result.getX(), result.getY());
		
		transform.transform(pt2, result);
		bBox.add(result.getX(), result.getY());
		bBox.updateShape();
		return bBox;
	}
	
	public Rectangle2D getRectangle() {
		return rectangle;
	}
	
	public double longLength() {
		double width = maxX - minX;
		double height = maxY - minY;
		if (width > height) {
			return width;
		} else {
			return height;
		}
	}
}
