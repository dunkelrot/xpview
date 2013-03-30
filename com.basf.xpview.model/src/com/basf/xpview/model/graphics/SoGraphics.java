package com.basf.xpview.model.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;


public class SoGraphics {

	protected Camera camera;
	
	public SoGraphics() {
		this.camera = new Camera();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void render(SoNode node, Graphics2D gc) {
		AffineTransform saveTA = gc.getTransform();
		gc.scale(camera.getZoom(), camera.getZoom());
		try {
			renderNode(node, gc);
		} catch (NoninvertibleTransformException ex) {
			System.out.println(ex.getMessage());
		}
		gc.setTransform(saveTA);
	}
	
	public void renderNode(SoNode node, Graphics2D gc) throws NoninvertibleTransformException {
		switch (node.getType()) {
		case PolyLine:
		case Shape:
		case Line:
			renderShape((SoShape)node, gc);
			break;			
		case Group:
			renderGroup((SoGroup)node, gc);
			break;
		case Circle:
			renderCircle((SoCircle)node, gc);
			break;			
		case Transformation:
			renderTransformation((SoGroup)node, gc);
			break;
		case Node:
			break;
		}
	}
	
	public void renderCircle(SoCircle circle, Graphics2D gc) throws NoninvertibleTransformException {
		if (camera.canSee(circle)) {
			AffineTransform at = gc.getTransform();
			if (circle.position.enabled) {
				gc.translate(circle.position.origin.x, circle.position.origin.y);
				gc.rotate(circle.getPosition().rotationAngle);
			}
			gc.scale(circle.scale.x, circle.scale.y);
			gc.setStroke(new TransformedStroke(circle.getStroke(), gc.getTransform()));
			gc.draw(circle.getShape());
			gc.setTransform(at);
		}
	}
	
	public void renderShape(SoShape shape, Graphics2D gc) throws NoninvertibleTransformException {
		if (camera.canSee(shape)) {
			AffineTransform at = gc.getTransform();
			if (shape.position.enabled) {
				gc.translate(shape.position.origin.x, shape.position.origin.y);
				gc.rotate(shape.getPosition().rotationAngle);
			}
			gc.scale(shape.scale.x, shape.scale.y);
			gc.setStroke(new TransformedStroke(shape.getStroke(), gc.getTransform()));
			gc.draw(shape.getShape());
			gc.setTransform(at);
		}
	}
	
	public void renderGroup(SoGroup group, Graphics2D gc) throws NoninvertibleTransformException {
		AffineTransform at = gc.getTransform();
		if (group.position.enabled) {
			gc.translate(group.position.origin.x, group.position.origin.y);
			gc.rotate(group.getPosition().rotationAngle);
		}
		gc.scale(group.scale.x, group.scale.y);
		for (SoNode node : group.getChildren()) {
			renderNode(node, gc);
		}
		gc.setTransform(at);
	}
	
	public void renderTransformation(SoGroup group, Graphics2D gc) throws NoninvertibleTransformException {
		for (SoNode node : group.getChildren()) {
			renderNode(node, gc);
		}
	}
	
}
