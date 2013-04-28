package org.dexpi.xpview.model.graphics;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;

public abstract class SoGeometry extends SoNode {

	protected Layer layer;
	protected BasicStroke stroke;
	
	public SoGeometry(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.stroke = new BasicStroke();
	}

	public SoGeometry(SoGeometry other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.stroke = new BasicStroke(other.stroke.getLineWidth());
		this.layer = new Layer(other.layer);
	}
	
	public void setLayer(Layer layer) {
		this.layer = layer;
	}
	
	public Layer getLayer() {
		return layer;
	}

	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}
	
	public Stroke getStroke() {
		return stroke;
	}
	
	public abstract Shape getShape();
	
}
