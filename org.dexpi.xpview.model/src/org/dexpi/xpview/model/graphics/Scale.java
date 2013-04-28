package org.dexpi.xpview.model.graphics;

public class Scale {
	public double x;
	public double y;
	
	public Scale() {
		this.x = 1.0;
		this.y = 1.0;
	}
	
	public Scale(Scale other) {
		this.x = other.x;
		this.y = other.y;
	}
}
