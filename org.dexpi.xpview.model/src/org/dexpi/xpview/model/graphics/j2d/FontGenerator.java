package org.dexpi.xpview.model.graphics.j2d;

import java.awt.Font;

import org.dexpi.xpview.model.graphics.SoNode;
import org.dexpi.xpview.model.graphics.SoText;
import org.dexpi.xpview.model.graphics.utils.NodeTraverser;
import org.dexpi.xpview.model.graphics.utils.NodeVisitor;

/**
 * This class is used to create the necessary {@link org.dexpi.xpview.model.graphics.FontData} objects
 * for each {@link SoText} node.
 * 
 * @author Arndt Teinert
 *
 */
public class FontGenerator {

	protected FontFactory fontFactory;
	
	public FontGenerator(FontFactory fontFactory) {
		this.fontFactory = fontFactory;
	}
	
	/**
	 * Creates {@link FontData} objects for all {@link SoText} nodes, starting at
	 * the given node.
	 * 
	 * @param node the node to start with
	 */
	public void createFonts(SoNode node) {
		
		NodeTraverser traverser = new NodeTraverser();
		traverser.traverse(node, new NodeVisitor() {
			
			@Override
			public void visit(SoNode node) {
				if (node instanceof SoText) {
					SoText textNode = (SoText) node;
					if (textNode.getFontData() == null) {
						Font font = fontFactory.createFont(textNode.getFontName(), textNode.getHeight(), 0, 0);
						textNode.setFontData(new FontData(font));
					}
				}
			}
		});
	}
	
}
