package com.basf.xpview.model.graphics;

import javax.vecmath.Point3d;

public class Camera {

	protected double zoom;
	
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
}
