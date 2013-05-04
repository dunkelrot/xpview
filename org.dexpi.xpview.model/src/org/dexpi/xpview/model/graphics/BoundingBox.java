package org.dexpi.xpview.model.graphics;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * 2D BoundingBox
 * 
 * @author Arndt Teinert
 *
 */
public class BoundingBox {

	protected double minX;
	protected double minY;
	
	protected double maxX;
	protected double maxY;
	
	protected boolean empty;
	
	protected Rectangle2D rectangle;
	
	/**
	 * Creates an empty {@link BoundingBox}
	 */
	public BoundingBox() {
		this.empty = true;
	}
	
	/**
	 * Makes the {@link BoundingBox} empty.
	 */
	public void clear() {
		maxX = 0;
		maxY = 0;
		minX = 0;
		minY = 0;
		this.empty = true;
	}
	
	/**
	 * Creates a copy of the given {@link BoundingBox}
	 * 
	 * @param other the box to copy
	 */
	public BoundingBox(BoundingBox other) {
		this.maxX = other.maxX;
		this.maxY = other.maxY;
		this.minX = other.minX;
		this.minY = other.minY;
		this.empty = other.empty;
	}

	/**
	 * Initializes the new box with the given values.
	 * 
	 * @param minX
	 * @param minY
	 * @param maxX
	 * @param maxY
	 */
	public BoundingBox(double minX, double minY, double maxX, double maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.minX = minX;
		this.minY = minY;
		this.empty = false;
	}
	
	/**
	 * Returns the maximal X coordinate.
	 * @return max X
	 */
	public double getMaxX() {
		return maxX;
	}
	
	/**
	 * Returns the maximal Y coordinate.
	 * @return max Y
	 */
	public double getMaxY() {
		return maxY;
	}
	
	/**
	 * Returns the minimal X coordinate.
	 * @return min X
	 */
	public double getMinX() {
		return minX;
	}

	/**
	 * Returns the minimal Y coordinate.
	 * @return min Y
	 */
	public double getMinY() {
		return minY;
	}

	/**
	 * Adds the given point to this box.
	 * @param x
	 * @param y
	 */
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
	
	/**
	 * Adds the given box to this box.
	 * 
	 * @param other the box to add
	 */
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
	
	/**
	 * Returns the center point of the box.
	 * @return the center point
	 */
	public Point3d getCenter() {
		Point3d pt = new Point3d(minX + (maxX - minX) * 0.5, minY + (maxY - minY) * 0.5, 0.0);
		return pt;
	}
	
	/**
	 * Returns the width (X-direction)
	 * @return
	 */
	public double getWidth() {
		return Math.abs(maxX - minX);
	}
	
	/**
	 * Returns the height (Y-direction)
	 * @return
	 */
	public double getHeight() {
		return Math.abs(maxY - minY);
	}
	
	/**
	 * Returns true if this box does not contain a single point.
	 * @return true or false
	 */
	public boolean isEmpty() {
		return empty;
	}
	
	/**
	 * Updates the internal shape. Used for debugging.
	 */
	public void updateShape() {
		rectangle = new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
	}
	
	/**
	 * Increases or decreases the box in X and Y direction
	 * @param value the scale factor
	 * 
	 */
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
	
	/**
	 * Returns a transformed box, this box is not modified.
	 * @param transform the transformation to apply.
	 * @return the transformed box.
	 */
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
	
	/**
	 * Returns the internal shape. You have to call updateShape once before!
	 * @return the internal shape (or null if updateShape has not been called)
	 */
	public Rectangle2D getRectangle() {
		return rectangle;
	}
	
	/**
	 * Width or height, which every is longer.
	 * @return width or height
	 */
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
