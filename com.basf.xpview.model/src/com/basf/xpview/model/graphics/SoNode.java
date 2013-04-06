package com.basf.xpview.model.graphics;

public abstract class SoNode {

	protected int id;
	protected String name;
	protected SoNode parent;
	protected BoundingBox boundingBox;
	protected NodeType type;
	protected SoMaterial selectionColor;
	
	public SoNode(SoNode parent, int id, String name) {
		this.name = name;
		this.id = id;
		this.parent = parent;
		this.boundingBox = new BoundingBox();
		this.type = NodeType.Node;
	}
	
	public SoNode(SoNode other, SoNode parent, IDProvider idProvider) {
		this.name = other.name;
		this.id = idProvider.getFreeId();
		this.parent = parent;
		this.boundingBox = new BoundingBox(other.boundingBox);
		this.type = other.type;
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
	
}
