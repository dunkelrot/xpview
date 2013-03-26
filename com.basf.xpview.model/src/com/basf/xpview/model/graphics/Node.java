package com.basf.xpview.model.graphics;

public abstract class Node {

	protected int id;
	
	abstract public void render();
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
}
