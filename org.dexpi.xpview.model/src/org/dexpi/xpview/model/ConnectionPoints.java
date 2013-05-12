package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This element is used to describe the potential flow connections from and 
 * to the parent of this element.  Each potential connection location is 
 * represented as a Node element.  Nodes are ordered and positioned on the drawing.  
 * Each Node element may be referenced by at most one Connection element.
 * 
 * The first node (known as Node 0) represents the geometric origin of the 
 * parent of the ConnectionPoints element.
 *
 */
public class ConnectionPoints implements PropertyProvider {

	protected ArrayList<Node> nodes;
	
	protected int flowIn = 1;
	protected int flowOut = 2;
	
	protected PropertyData propertyData;
	protected Connectable connectable;
	
	public ConnectionPoints(Connectable connectable) {
		this.nodes = new ArrayList<Node>();
		this.propertyData = new PropertyData(this);
		this.connectable = connectable;
	}
	
	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
	public void setFlowIn(int flowIn) {
		this.flowIn = flowIn;
	}
	
	public void setFlowOut(int flowOut) {
		this.flowOut = flowOut;
	}
	
	public int getFlowIn() {
		return flowIn;
	}
	
	public int getFlowOut() {
		return flowOut;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public Node getFirstNode() {
		if (nodes.size() != 0) {
			return nodes.get(0);
		} else {
			return null;
		}
	}

	public Connectable getConnectable() {
		return connectable;
	}
}
