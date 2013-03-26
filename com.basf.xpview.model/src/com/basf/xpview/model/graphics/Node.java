package com.basf.xpview.model.graphics;

public abstract class Node {

	protected int id;
	protected String name;
	protected Node parent;
	
	public Node(Node parent, int id, String name) {
		this.name = name;
		this.id = id;
		this.parent = parent;
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
	
	public Node getParent() {
		return parent;
	}
	
	public abstract void render(Context context);
	
}
