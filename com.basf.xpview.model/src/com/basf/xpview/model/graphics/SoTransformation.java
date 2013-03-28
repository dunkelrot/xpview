package com.basf.xpview.model.graphics;

public class SoTransformation extends SoGroup {

	public SoTransformation(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.Transformation;
	}
	
	
	
}
