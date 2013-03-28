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
	}

	public void init(Point3d[] points) {
		path = new Path2D.Double(Path2D.WIND_EVEN_ODD, points.length);
		boolean isFirst = true;
		for (Point3d point : points) {
			if (isFirst) {
				path.moveTo(point.x, point.y);
				isFirst = false;
			} else {
				path.lineTo(point.x, point.y);
			}
		}
	}
	
	public Shape getShape() {
		return path;
	}
	
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	public boolean isFilled() {
		return filled;
	}
	
	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		SoShape copy = new SoShape(parent, idProvider.getFreeId(), name);
		copy.path = new Path2D.Double(path);
		copy.filled = filled;
		return copy;
	}	
}
