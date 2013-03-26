package com.basf.xpview.model.graphics;

public abstract class Context {

	public void pushMaterial(Material material) {
		
	}
	
	public void pushTransformation(Transformation transformation) {
		
	}
	
	public void popMaterial() {
		
	}
	
	public void popTransformation() {
		
	}
	
	public abstract void renderLine(double x1, double y1, double x2, double y2);
	
}
