package org.dexpi.xpview.model.graphics;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import org.dexpi.xpview.model.io.xmplant.Text;

public class SoPoint extends SoGeometry {

	protected Ellipse2D.Double circle;
	
	public SoPoint(SoNode parent, int id) {
		super(parent, id, Text.class.getSimpleName());
		this.type = NodeType.Point;
	}
	
	public SoPoint(SoPoint other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.type = NodeType.Text;
		init();
	}

	public void init() {
		this.circle = new Ellipse2D.Double(-1, -1, +1, +1);
	}
	
	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		return new SoPoint(this, parent, idProvider);
	}

	@Override
	public void scale(double x, double y) {
		// do nothing
	}
	
	@Override
	public Shape getShape() {
		return circle;
	}
}
