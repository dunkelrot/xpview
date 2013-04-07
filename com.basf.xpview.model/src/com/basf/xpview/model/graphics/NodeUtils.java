package com.basf.xpview.model.graphics;

import java.awt.geom.AffineTransform;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class NodeUtils {

	public AffineTransform getOverallTransform(SoNode node) {
		ArrayDeque<AffineTransform> transformList = new ArrayDeque<AffineTransform>();
		getTransform(node, transformList);

		AffineTransform transform = new AffineTransform();
		Iterator<AffineTransform> itor = transformList.descendingIterator();
		while (itor.hasNext()) {
			transform.concatenate(itor.next());
		}

		return transform;
	}

	protected void getTransform(SoNode node, Deque<AffineTransform> transformList) {
		if (node instanceof SoTransformation) {
			SoTransformation transformNode = (SoTransformation) node;
			if (transformNode.position.isEnabled()) {
				AffineTransform transform = new AffineTransform();
				transform.translate(transformNode.position.origin.x, transformNode.position.origin.y);
				transform.rotate(transformNode.position.rotationAngle);
				transformList.add(transform);
			}
		}
		if (node.getParent() != null) {
			getTransform(node.getParent(), transformList);
		}
	}

	static public void updateBoundingBoxShapes(SoNode node) {
		if (node instanceof SoGroup) {
			for (SoNode child : ((SoGroup) node).getChildren()) {
				updateBoundingBoxShapes(child);
			}
		}
		node.getBoundingBox().updateShape();
	}

	static public void getNodesAt(SoGroup node, AffineTransform transform, double x, double y, List<SoNode> foundNodes) {

		if (node.getBoundingBox(transform).getRectangle().contains(x, y)) {
			if (node.isSelectable()) {
				foundNodes.add(node);
			}
			for (SoNode child : ((SoGroup) node).getChildren()) {
				if (child instanceof SoTransformation) {
					SoTransformation transformNode = (SoTransformation) child;
					transform = new AffineTransform(transform);
					transform.translate(transformNode.position.origin.x, transformNode.position.origin.y);
					transform.rotate(transformNode.position.rotationAngle);
				}
				if (child instanceof SoGroup) {
					getNodesAt((SoGroup) child, transform, x, y, foundNodes);
				}
			}
		}
	}

}
