package org.dexpi.xpview.model.graphics;

import java.util.HashMap;
import java.util.Map;

public class RepresentationManager implements IDProvider {

	protected int currentId;
	protected HashMap<Object, SoNode> object2Node;
	protected HashMap<SoNode, Object> node2Object;
	static private RepresentationManager instance;
	
	private RepresentationManager() {
		this.object2Node = new HashMap<Object, SoNode>();
		this.node2Object = new HashMap<SoNode, Object>();
		this.currentId = 0;
	}
	
	public Map<Object, SoNode> getRepresentations() {
		return object2Node;
	}
	
	public SoNode getNode(Object object) {
		return object2Node.get(object);
	}
	
	public Object getObject(SoNode node) {
		return node2Object.get(node);
	}
	
	public void addNode(SoNode node, Object object) {
		object2Node.put(object, node);
		node2Object.put(node, object);
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
	
	public void clear() {
		object2Node.clear();
		node2Object.clear();
	}
	
}
