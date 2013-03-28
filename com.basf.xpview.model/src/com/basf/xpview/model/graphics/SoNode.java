package com.basf.xpview.model.graphics;

public abstract class SoNode {

	protected int id;
	protected String name;
	protected SoNode parent;
	protected BoundingBox boundingBox;
	protected NodeType type;
	protected Position position;
	protected Scale scale;
	
	public SoNode(SoNode parent, int id, String name) {
		this.name = name;
		this.id = id;
		this.parent = parent;
		this.boundingBox = new BoundingBox();
		this.type = NodeType.Node;
		this.position = new Position();
		this.scale = new Scale();
	}
	
	public SoNode(SoNode other, SoNode parent, IDProvider idProvider) {
		this.name = other.name;
		this.id = idProvider.getFreeId();
		this.parent = parent;
		this.boundingBox = new BoundingBox(other.boundingBox);
		this.type = other.type;
		this.position = new Position(other.position);
		this.scale = new Scale(other.scale);
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
	
	public Position getPosition() {
		return position;
	}
	
	public Scale getScale() {
		return scale;
	}
	
	abstract public SoNode clone(SoNode parent, IDProvider idProvider);
	
}
