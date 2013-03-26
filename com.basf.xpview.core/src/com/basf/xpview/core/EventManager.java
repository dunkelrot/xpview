package com.basf.xpview.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EventManager {

	private HashMap<EventType, List<EventListener>> eventTypeListeners;
	
	static private EventManager instance;
	
	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}
		return instance;
	}
	
	private EventManager() {
		this.eventTypeListeners = new HashMap<EventType, List<EventListener>>();
	}
	
	public void registerForEvent(EventType type, EventListener listener) {
		List<EventListener> listeners = eventTypeListeners.get(type);
		if (listeners == null) {
			listeners = new ArrayList<EventListener>();
			eventTypeListeners.put(type, listeners);
		}
		listeners.add(listener);
	}
	
	public void sendEvent(Event event) {
		List<EventListener> listeners = eventTypeListeners.get(event.type);
		if (listeners != null) {
			for (EventListener eventListener : listeners) {
				eventListener.onEvent(event);
			} 
		}
	}
	
	public void unregisterForEvent(EventType type, EventListener listener) {
		List<EventListener> listeners = eventTypeListeners.get(type);
		if (listeners != null) {
			listeners.remove(listener);
		}
	}
	
}
