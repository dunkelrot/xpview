package org.dexpi.xpview.model.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import javax.vecmath.Point3d;

public class Camera {

	protected double zoom;
	protected double width;
	protected double height;
	protected Point3d eye;
	protected Point3d direction;
	protected Point3d up;

	public Camera() {
		this.zoom = 1.0;
		this.eye = new Point3d();
	}
	
	public boolean canSee(SoNode node) {
		return true;
	}
	
	public double getZoom() {
		return zoom;
	}
	
	public Point3d getDirection() {
		return direction;
	}
	
	public Point3d getEye() {
		return eye;
	}
	
	public Point3d getUp() {
		return up;
	}
	
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	
	public void moveBy(int x, int y) {
		eye.x = eye.x + x;
		eye.y = eye.y + y;
	}
	
	public void moveByZoomed(int x, int y) {
		eye.x = eye.x + (x / zoom);
		eye.y = eye.y + (y / zoom);
	}
	
	public void lookAt(Point3d point) {
		eye = point;
	}
	
	public Point2D screenToWorld(Point2D screen) throws NoninvertibleTransformException {
		AffineTransform sToW = new AffineTransform();
		sToW.translate(width * 0.5, height * 0.5);
		sToW.scale(zoom, -zoom);
		sToW.translate(-eye.x, -eye.y);
	    sToW.invert();
		Point2D worldPt = new Point2D.Double();
		return sToW.transform(screen, worldPt);
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getWidth() {
		return width;
	}
	
	public void setHeight(double height) {
		this.height = height;
	} 
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public void transform(Graphics2D gc) {
		gc.translate(width * 0.5, height * 0.5);
		gc.scale(zoom, -zoom);
		gc.translate(-eye.x, -eye.y);		
	}
	
	public void zoomTo(BoundingBox bBox) {
		
	}
}
