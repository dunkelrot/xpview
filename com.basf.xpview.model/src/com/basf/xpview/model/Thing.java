package com.basf.xpview.model;

public abstract class Thing {

	protected String ID;
	protected String name;
	protected String description;
	
	public Thing(String name, String ID) {
		this.name = name;
		this.ID = ID;
	}
	
	public Thing(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setID(String iD) {
		ID = iD;
	}
}
