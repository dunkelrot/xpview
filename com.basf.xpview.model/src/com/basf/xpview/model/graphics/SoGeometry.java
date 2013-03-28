package com.basf.xpview.model.graphics;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;

public abstract class SoGeometry extends SoNode {

	protected Layer layer;
	protected Stroke stroke;
	
	public SoGeometry(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.stroke = new BasicStroke();
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}
	
	public Layer getLayer() {
		return layer;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}
	
	public Stroke getStroke() {
		return stroke;
	}
	
	public abstract Shape getShape();
	
}
