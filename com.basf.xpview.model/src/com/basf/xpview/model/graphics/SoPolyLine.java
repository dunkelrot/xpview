package com.basf.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Path2D;

import javax.vecmath.Point3d;

public class SoPolyLine extends SoCurve {

	protected Path2D.Double path;
	
	public SoPolyLine(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.PolyLine;
	}
	
	public SoPolyLine(SoPolyLine other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.type = NodeType.PolyLine;
		this.path = new Path2D.Double(other.path);
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
	
	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		return new SoPolyLine(this, parent, idProvider);
	}

	@Override
	public Shape getShape() {
		return path;
	}
}
