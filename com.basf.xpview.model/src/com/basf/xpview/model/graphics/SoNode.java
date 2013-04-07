package com.basf.xpview.model.graphics;

import java.awt.geom.AffineTransform;

public abstract class SoNode {

	protected int id;
	protected String name;
	protected SoNode parent;
	protected BoundingBox boundingBox;
	protected NodeType type;
	protected SoMaterial selectionColor;
	protected boolean selectable;
	
	public SoNode(SoNode parent, int id, String name) {
		this.name = name;
		this.id = id;
		this.parent = parent;
		this.boundingBox = new BoundingBox();
		this.type = NodeType.Node;
		this.selectable = true;
	}
	
	public SoNode(SoNode other, SoNode parent, IDProvider idProvider) {
		this.name = other.name;
		this.id = idProvider.getFreeId();
		this.parent = parent;
		this.boundingBox = new BoundingBox(other.boundingBox);
		this.type = other.type;
		this.selectable = other.selectable;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public SoNode getParent() {
		return parent;
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	
	public BoundingBox getBoundingBox(AffineTransform transform) {
		return boundingBox.transformed(transform);
	}
	
	public NodeType getType() {
		return type;
	}
	
	public void setSelectionColor(SoMaterial selectionColor) {
		this.selectionColor = selectionColor;
	}
	
	public SoMaterial getSelectionColor() {
		return selectionColor;
	}
	
	abstract public void scale(double x, double y);
	
	abstract public SoNode clone(SoNode parent, IDProvider idProvider);
	
	public boolean isSelectable() {
		return selectable;
	}
	
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	
}
