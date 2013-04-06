package com.basf.xpview.model.graphics;

import java.awt.Color;

public class SoMaterial extends SoNode {

	protected Color color;

	public SoMaterial(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.color = Color.BLACK;
	}
	
	public SoMaterial(SoMaterial other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.color = new Color(other.color.getRed(), other.color.getGreen(), other.color.getBlue(), other.color.getAlpha());
	}

	public void init(int red, int green, int blue, int alpha) {
		this.color = new Color(red, green, blue, alpha);
	}
	
	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		return new SoMaterial(this, parent, idProvider);
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public void scale(double x, double y) {
		// ignored
	}
}
