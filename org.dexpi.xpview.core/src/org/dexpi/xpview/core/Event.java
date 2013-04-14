package com.basf.xpview.core;

public class Event {

	protected Object source;
	protected EventType type;
	protected Object data;
	
	public Event(Object source, EventType type, Object data) {
		this.source = source;
		this.type = type;
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}
	
	public EventType getType() {
		return type;
	}
	
	public Object getSource() {
		return source;
	}
	
	
}
