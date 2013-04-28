package org.dexpi.xpview.model.graphics;

import java.awt.Graphics2D;

public abstract class Context {

	public Graphics2D gc;
	
	public void pushMaterial(SoMaterial material) {
		
	}
	
	public void pushTransformation(SoTransformation transformation) {
		
	}
	
	public void popMaterial() {
		
	}
	
	public void popTransformation() {
		
	}
	
	public abstract void renderLine(double x1, double y1, double x2, double y2);
	
	
}
