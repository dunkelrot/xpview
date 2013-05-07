package org.dexpi.xpview.model.graphics.utils;

import org.dexpi.xpview.model.graphics.SoNode;

/**
 * 
 * @author Arndt Teinert
 *
 */
public interface NodeVisitor {

	/**
	 * This method is called by the {@link NodeTraverser} for each visited node.
	 * @param node the node to process
	 */
	void visit(SoNode node);
	
}
