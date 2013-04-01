package com.basf.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Path2D;

import javax.vecmath.Point3d;



public class SoShape extends SoGeometry {

	protected Path2D.Double path;
	protected boolean filled;
	
	public SoShape(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.Shape;
		this.filled = false;
	}

	public SoShape(SoShape other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.type = NodeType.Shape;
		this.filled = other.filled;
		this.path = new Path2D.Double(other.path);
	}
	
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	public void init(Point3d[] points) {
		path = new Path2D.Double(Path2D.WIND_EVEN_ODD, points.length);
		boolean isFirst = true;
		for (Point3d point : points) {
			boundingBox.add(point.x, point.y);
			if (isFirst) {
				path.moveTo(point.x, point.y);
				isFirst = false;
			} else {
				path.lineTo(point.x, point.y);
			}
		}
	}
	
	public boolean isFilled() {
		return filled;
	}
	
	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		return new SoShape(this, parent, idProvider);
	}
	
	@Override
	public Shape getShape() {
		return path;
	}

}
