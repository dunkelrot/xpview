package com.basf.xpview.model.graphics;

import java.util.HashMap;
import java.util.Map;

public class RepresentationManager implements IDProvider {

	protected int currentId;
	protected HashMap<Object, SoNode> representations;
	static private RepresentationManager instance;
	
	private RepresentationManager() {
		this.representations = new HashMap<Object, SoNode>();
		this.currentId = 0;
	}
	
	public Map<Object, SoNode> getRepresentations() {
		return representations;
	}
	
	public SoNode getNode(Object object) {
		return representations.get(object);
	}
	
	public void addNode(SoNode node, Object object) {
		representations.put(object, node);
	}
	
	public int getFreeId() {
		currentId += 1;
		return currentId;
	}
	
	static public RepresentationManager getInstance() {
		if (instance == null) {
			instance = new RepresentationManager();
		}
		return instance;
	}
	
}
