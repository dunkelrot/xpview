package org.dexpi.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Path2D;

import javax.vecmath.Point3d;

public class SoLine extends SoCurve {

	protected Path2D.Double path;
	protected Point3d[] points;
	
	public SoLine(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.Line;
	}
	
	public SoLine(SoLine other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.type = NodeType.Line;
		this.path = new Path2D.Double(other.path);
		this.points = new Point3d[other.points.length];
		for (int ii = 0; ii < other.points.length; ii++) {
			points[ii] = new Point3d();
			points[ii].x = other.points[ii].x;
			points[ii].y = other.points[ii].y;
			points[ii].z = other.points[ii].z;
		}	
	}

	public void init(Point3d start, Point3d end) {
		path = new Path2D.Double(Path2D.WIND_EVEN_ODD, 2);
		path.moveTo(start.x, start.y);
		path.lineTo(end.x, end.y);
		this.points = new Point3d[2];
		points[0] = start;
		points[1] = end;
	}
	
	public void init(Point3d[] points) {
		this.points = points;
		scale(1,1);
	}
	
	public void scale(double x, double y) {
		boundingBox.clear();
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
