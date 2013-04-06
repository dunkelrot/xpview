package com.basf.xpview.model.graphics;

public class SoTransformation extends SoGroup implements SoPositionNode {

	public Position position;
	public Scale scale;
	
	public SoTransformation(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.Transformation;
		this.position = new Position();
		this.scale = new Scale();
	}
	
	public SoTransformation(SoTransformation other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.type = other.type;
		this.position = new Position(other.position);
		this.scale = new Scale(other.scale);
	}
	
	public Scale getScale() {
		return scale;
	}
	
	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		return new SoTransformation(this, parent, idProvider);
	}
	
	@Override
	public void scale(double x, double y) {
		super.scale(x, y);
	}

	@Override
	public Position getPosition() {
		return position;
	}
}
