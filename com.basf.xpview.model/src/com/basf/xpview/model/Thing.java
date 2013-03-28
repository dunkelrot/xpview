package com.basf.xpview.model;

public abstract class Thing {

	protected String name;

	public Thing(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
