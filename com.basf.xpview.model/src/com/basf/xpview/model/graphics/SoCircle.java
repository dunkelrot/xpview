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
		this.circle = new Ellipse2D.Double(-radius,-radius,radius * 2.0, radius * 2.0);
		boundingBox.add(-radius, -radius);
		boundingBox.add(radius, radius);
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
