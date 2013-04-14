package com.basf.xpview.model.graphics;

import java.util.ArrayList;

public class SoSelectionManager {
	
	protected ArrayList<SoNode> selectedNodes;
	protected SoMaterial selectionColor;
	
	public SoSelectionManager() {
		this.selectedNodes = new ArrayList<SoNode>();
		this.selectionColor = new SoMaterial(null, 0, "SelectionColor");
		this.selectionColor.init(255, 0, 0, 255);
	}
	
	public ArrayList<SoNode> getSelectedNodes() {
		return selectedNodes;
	}
	
	public void addNode(SoNode node) {
		node.setSelectionColor(selectionColor);
		selectedNodes.add(node);
	}
	
	public void clear() {
		for (SoNode node : selectedNodes) {
			node.setSelectionColor(null);
		}
		selectedNodes.clear();
	}
	
	public int size() {
		return selectedNodes.size();
	}


}
