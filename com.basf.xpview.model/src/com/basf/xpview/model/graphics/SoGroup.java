package com.basf.xpview.model.graphics;

import java.util.ArrayList;
import java.util.List;

public class SoGroup extends SoNode {

	protected ArrayList<SoNode> children;
	
	public SoGroup(SoNode parent, int id, String name) {
		super(parent, id, name);
		this.type = NodeType.Group;
		this.children = new ArrayList<SoNode>();
	}
	
	public SoGroup(SoGroup other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.children = new ArrayList<SoNode>();
		for (SoNode child : other.children) {
			SoNode childCopy = child.clone(this, idProvider);
			this.children.add(childCopy);
		}
	}
	
	public List<SoNode> getChildren() {
		return children;
	}
	
	public void addNode(SoNode node) {
		children.add(node);
	}

	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		return new SoGroup(this, parent, idProvider);
	}
	
}
