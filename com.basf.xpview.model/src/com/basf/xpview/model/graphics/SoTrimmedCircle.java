package com.basf.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Arc2D;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

public class SoTrimmedCircle extends SoCurve implements SoPositionNode {

	protected Arc2D.Double circle;
	protected double radius;
	protected double start;
	protected double end;
	public Position position;
	
	public SoTrimmedCircle(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.TrimmedCircle;
		this.position = new Position();
	}

	public SoTrimmedCircle(SoTrimmedCircle other, SoNode parent,
			IDProvider idProvider) {
		super(other, parent, idProvider);
		this.type = NodeType.TrimmedCircle;
		this.radius = other.radius;
		this.start = other.start;
		this.end = other.end;
		this.position = new Position(other.position);
		this.circle = new Arc2D.Double(other.circle.x, other.circle.y,
				other.circle.width, other.circle.height, other.circle.start,
				other.circle.extent, other.circle.getArcType());
	}

	public void init(double radius, double start, double end) {
		this.start = start;
		this.end = end;
		this.radius = radius;
		scale(1,1);
	}

	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		return new SoTrimmedCircle(this, parent, idProvider);
	}

	@Override
	public Shape getShape() {
		return circle;
	}

	public double getRadius() {
		return radius;
	}
	
	@Override
	public void scale(double x, double y) {
		double extend = (end - start);
		if (end < start) {
			extend = 360.0 - start + end;
		}
		
		Matrix4d transform = new Matrix4d();
		transform.setIdentity();
		transform.setM00(x);
		transform.setM11(y);
		
		Point3d pt1 = new Point3d(-radius, -radius, 0);
		Point3d pt2 = new Point3d(radius * 2.0, radius * 2.0, 0);
		
		transform.transform(pt1);
		transform.transform(pt2);
		
		this.circle = new Arc2D.Double(pt1.x, pt1.y, pt2.x, pt2.y, start, extend, Arc2D.OPEN);
		
		boundingBox.clear();
		boundingBox.add(pt1.x, pt1.y);
		boundingBox.add(pt1.x + pt2.x, pt1.y + pt2.y);
		
		position.origin.x = position.origin.x * x;
		position.origin.y = position.origin.y * y;
	}
	
	@Override
	public Position getPosition() {
		return position;
	}
	
}
