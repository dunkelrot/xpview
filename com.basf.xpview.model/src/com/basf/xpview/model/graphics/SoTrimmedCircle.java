package com.basf.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Arc2D;

public class SoTrimmedCircle extends SoCurve {

	protected Arc2D.Double circle;
	protected double radius;

	public SoTrimmedCircle(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.TrimmedCircle;
	}

	public SoTrimmedCircle(SoTrimmedCircle other, SoNode parent,
			IDProvider idProvider) {
		super(other, parent, idProvider);
		this.type = NodeType.TrimmedCircle;
		this.radius = other.radius;
		this.circle = new Arc2D.Double(other.circle.x, other.circle.y,
				other.circle.width, other.circle.height, other.circle.start,
				other.circle.extent, other.circle.getArcType());
	}

	public void init(double radius, double start, double end) {
		this.radius = radius;
		double extend = (end - start);
		if (end < start) {
			extend = 360.0 - start + end;
		}
		this.circle = new Arc2D.Double(-radius, -radius, radius * 2.0,
				radius * 2.0, start, extend, Arc2D.OPEN);
		boundingBox.add(-radius, -radius);
		boundingBox.add(radius, radius);
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
}
