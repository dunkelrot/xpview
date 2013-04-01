package com.basf.xpview.model.graphics;


public abstract class SoCurve extends SoGeometry {

	public SoCurve(SoNode parent, int id, String name) {
		super(parent, id, name);
	}

	public SoCurve(SoCurve other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
	}
}
