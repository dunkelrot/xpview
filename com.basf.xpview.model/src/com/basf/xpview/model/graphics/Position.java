package com.basf.xpview.model.graphics;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Position {
	
	public Point3d origin;
	public Vector3d axis;
	public Vector3d direction;
	public double rotationAngle;
	
	public boolean enabled;
	
	public Position() {
		this.enabled = true;
		this.origin = new Point3d();
		this.axis = new Vector3d();
		this.direction = new Vector3d();
	}
	
	public Position(Position other) {
		this.enabled = other.enabled;
		this.origin = new Point3d(other.origin);
		this.axis = new Vector3d(other.axis);
		this.direction = new Vector3d(other.direction);
		
		normalize();
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void normalize() {
		if (direction.length() < 0.000001) {
			direction.x = 1.0;
			direction.y = 0.0;
			direction.z = 0.0;
		}
		direction.normalize();
		axis.normalize();
	}
	
	public void calculateRotationAngle() {
		Vector3d axisX = new Vector3d(1,0,0);
		rotationAngle = Math.acos(direction.dot(axisX));
		if (direction.y < 0) {
			rotationAngle *= -1.0;
		}
		// System.out.println(rotationAngle);
	}

}
