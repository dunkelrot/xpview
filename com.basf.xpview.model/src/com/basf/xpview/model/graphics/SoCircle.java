package com.basf.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;


public class SoCircle extends SoGeometry {
	
	protected Ellipse2D.Double circle;
	protected double radius;
	protected boolean filled;
	
	public SoCircle(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.Circle;
	}

	public SoCircle(SoCircle other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.type = NodeType.Circle;
		this.radius = other.radius;
		this.filled = other.filled;
		this.circle = new Ellipse2D.Double(-radius,-radius,radius * 2.0, radius * 2.0);
	}
	
	public void init(double radius, boolean filled) {
		this.radius = radius;
		this.filled = filled;
		scale(1,1);
	}
	
	public void scale(double x, double y) {
		
		Matrix4d transform = new Matrix4d();
		transform.setIdentity();
		transform.setM00(x);
		transform.setM11(y);
		
		Point3d pt1 = new Point3d(-radius, -radius, 0);
		Point3d pt2 = new Point3d(radius * 2.0, radius * 2.0, 0);
		
		transform.transform(pt1);
		transform.transform(pt2);
		
		this.circle = new Ellipse2D.Double(pt1.x, pt1.y, pt2.x, pt2.y);
		
		boundingBox.clear();
		boundingBox.add(pt1.x, pt1.y);
		boundingBox.add(pt2.x, pt2.y);
	}
	
	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		return new SoCircle(this, parent, idProvider);
	}

	@Override
	public Shape getShape() {
		return circle;
	}
	
	public boolean isFilled() {
		return filled;
	}
	
	public double getRadius() {
		return radius;
	}
	
	
}
