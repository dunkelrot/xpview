package org.dexpi.xpview.model.graphics;

public abstract class SoNodeT extends SoNode {

	public Position position;
	public Scale scale;
	
	public SoNodeT(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.Transformation;
		this.position = new Position();
		this.scale = new Scale();
	}
	
	public SoNodeT(SoNodeT other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.type = other.type;
		this.position = new Position(other.position);
		this.scale = new Scale(other.scale);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public Scale getScale() {
		return scale;
	}
	
}
