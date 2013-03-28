package com.basf.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;


public class SoCircle extends SoGeometry {
	
	protected Ellipse2D.Double circle;
	protected double radius;
	protected boolean filled;
	
	public SoCircle(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.Circle;
	}

	public void init(double radius, boolean filled) {
		this.radius = radius;
		this.filled = filled;
		this.circle = new Ellipse2D.Double(-radius,-radius,radius * 2.0, radius * 2.0);
	}
	
	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		SoCircle copy = new SoCircle(parent, idProvider.getFreeId(), name);
		copy.radius = this.radius;
		copy.circle = new Ellipse2D.Double(circle.x, circle.y, circle.width, circle.height);
		return copy;
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
