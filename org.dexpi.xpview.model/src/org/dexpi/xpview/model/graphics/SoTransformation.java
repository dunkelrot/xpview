package org.dexpi.xpview.model.graphics;

import java.awt.geom.AffineTransform;

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
	public BoundingBox getBoundingBox() {
		AffineTransform transform = new AffineTransform();
		if (position.enabled) {
			transform.translate(position.origin.x, position.origin.y);
			transform.rotate(position.rotationAngle);
		}
		boundingBox.clear();
		for (SoNode child : children) {
			boundingBox.add(child.getBoundingBox().transformed(transform));
		}
		return boundingBox;
	}
	
	@Override
	public Position getPosition() {
		return position;
	}
}
