package com.basf.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Path2D;

import javax.vecmath.Point3d;

public class SoLine extends SoCurve {

	protected Path2D.Double path;
	
	public SoLine(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.Line;
	}
	
	public SoLine(SoLine other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.type = NodeType.Line;
		this.path = new Path2D.Double(other.path);
	}

	public void init(Point3d start, Point3d end) {
		path = new Path2D.Double(Path2D.WIND_EVEN_ODD, 2);
		path.moveTo(start.x, start.y);
		path.lineTo(end.x, end.y);
	}
	
	public void init(Point3d[] points) {
		path = new Path2D.Double(Path2D.WIND_EVEN_ODD, points.length);
		boolean isFirst = true;
		for (Point3d point : points) {
			boundingBox.add(point.x,  point.y);
			if (isFirst) {
				path.moveTo(point.x, point.y);
				isFirst = false;
			} else {
				path.lineTo(point.x, point.y);
			}
		}
	}
	
	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		return new SoLine(this, parent, idProvider);
	}

	@Override
	public Shape getShape() {
		return path;
	}
}
