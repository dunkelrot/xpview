package org.dexpi.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Path2D;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;



public class SoShape extends SoGeometry {

	protected Path2D.Double path;
	protected boolean filled;
	protected Point3d[] points;
	
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
		
		this.points = new Point3d[other.points.length];
		for (int ii = 0; ii < other.points.length; ii++) {
			points[ii] = new Point3d();
			points[ii].x = other.points[ii].x;
			points[ii].y = other.points[ii].y;
			points[ii].z = other.points[ii].z;
		}
	}
	
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	public void init(Point3d[] points) {
		this.points = points;
		scale(1,1);
	}
	
	public void scale(double x, double y) {
		
		boundingBox.clear();
		
		Matrix4d transform = new Matrix4d();
		transform.setIdentity();
		transform.setM00(x);
		transform.setM11(y);
		
		path = new Path2D.Double(Path2D.WIND_EVEN_ODD, points.length);
		boolean isFirst = true;
		for (Point3d point : points) {
			transform.transform(point);
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
