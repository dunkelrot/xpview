package com.basf.xpview.core;

public class EventType {

	protected String name;
	
	public EventType(String name) {
		this.name = name;
	}
	
	public boolean is(String name) {
		return this.name.equals(name);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EventType) {
			return ((EventType) obj).name.equals(name);
		} else {
			return super.equals(obj);
		}
	}
}
