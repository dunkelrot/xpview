package org.dexpi.xpview.model.graphics.utils;

import org.dexpi.xpview.model.graphics.SoGroup;
import org.dexpi.xpview.model.graphics.SoNode;

/**
 * This class can be used to execute code on each and every node.  
 * 
 * @author Arndt Teinert
 *
 */
public class NodeTraverser {

	/**
	 * Starts with the given node and traverses all child nodes.
	 * Calls the {@link NodeVisitor}s visit method for every visited node.
	 *  
	 * @param node the node to start with
	 * @param visitor 
	 */
	public void traverse(SoNode node, NodeVisitor visitor) {
		visitor.visit(node);
		if (node instanceof SoGroup) {
			SoGroup group = (SoGroup) node;
			for (SoNode child : group.getChildren()) {
				traverse(child, visitor);
			}
		}
	}
	
}
