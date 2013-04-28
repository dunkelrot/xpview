package org.dexpi.xpview.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.widgets.Display;


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
	
	public void sendEvent(final Event event) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				List<EventListener> listeners = eventTypeListeners.get(event.type);
				if (listeners != null) {
					for (EventListener eventListener : listeners) {
						eventListener.onEvent(event);
					} 
				}
			}
		});
	}
	
	public void unregisterForEvent(EventType type, EventListener listener) {
		List<EventListener> listeners = eventTypeListeners.get(type);
		if (listeners != null) {
			listeners.remove(listener);
		}
	}
	
}
