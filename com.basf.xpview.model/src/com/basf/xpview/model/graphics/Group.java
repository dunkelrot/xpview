package com.basf.xpview.model.graphics;

import java.util.ArrayList;
import java.util.List;

public class Group extends Node {

	protected ArrayList<Node> children;
	
	public Group(Node parent, int id, String name) {
		super(parent, id, name);
		this.children = new ArrayList<Node>();
	}
	
	public List<Node> getChildren() {
		return children;
	}
	
	@Override
	public void render(Context context) {
		for (Node child: children) {
			child.render(context);
		}
	}
	
}
