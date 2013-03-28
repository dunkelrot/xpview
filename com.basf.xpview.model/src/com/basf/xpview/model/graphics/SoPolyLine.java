package com.basf.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Path2D;

import javax.vecmath.Point3d;

public class SoPolyLine extends SoCurve {

	public SoPolyLine(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.PolyLine;
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
	
	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		SoPolyLine copy = new SoPolyLine(parent, idProvider.getFreeId(), name);
		copy.path = new Path2D.Double(path);
		return copy;
	}

}
