package com.basf.xpview.model.graphics.j2d;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayDeque;
import java.util.Deque;

import com.basf.xpview.model.graphics.Camera;
import com.basf.xpview.model.graphics.SoCircle;
import com.basf.xpview.model.graphics.SoGeometry;
import com.basf.xpview.model.graphics.SoGroup;
import com.basf.xpview.model.graphics.SoMaterial;
import com.basf.xpview.model.graphics.SoNode;
import com.basf.xpview.model.graphics.SoText;
import com.basf.xpview.model.graphics.SoTransformation;
import com.basf.xpview.model.graphics.SoTrimmedCircle;
import com.basf.xpview.model.graphics.TransformedStroke;


public class Graphics {

	protected Camera camera;
	protected Deque<SoMaterial> colorStack;
	protected boolean selectionColorActive;
	
	public Graphics() {
		this.camera = new Camera();
		this.colorStack = new ArrayDeque<SoMaterial>(64);
		SoMaterial defaultMaterial = new SoMaterial(null, 0, "Default");
		defaultMaterial.init(0, 0, 0, 255);
		this.colorStack.push(defaultMaterial);
		
		this.selectionColorActive = false;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void render(SoNode node, Graphics2D gc) {
		AffineTransform saveTA = gc.getTransform();
		gc.scale(camera.getZoom(), camera.getZoom());
		gc.translate(- camera.getEye().x, - camera.getEye().y);
		try {
			renderNode(node, gc);
		} catch (NoninvertibleTransformException ex) {
			System.out.println(ex.getMessage());
		}
		gc.setTransform(saveTA);
	}
	
	protected void pushSelectionColor(SoNode node) {
		if (node.getSelectionColor() != null) {
			colorStack.push(node.getSelectionColor());
			selectionColorActive = true;
		}
	}
	
	protected void popSelectionColor(SoNode node) {
		if (node.getSelectionColor() != null) {
			colorStack.pop();
			selectionColorActive = false;
		}
	}	
	
	protected void pushColor(SoNode node) {
		if (selectionColorActive == false) {
			colorStack.push(node.getSelectionColor());
		}
	}

	protected void popColor(SoNode node) {
		if (selectionColorActive == false) {
			colorStack.pop();
		}
	}
	
	public void renderNode(SoNode node, Graphics2D gc) throws NoninvertibleTransformException {

		pushSelectionColor(node);
		gc.setColor(colorStack.peek().getColor());
		
		switch (node.getType()) {
		case PolyLine:
		case Shape:
		case Line:
			renderGeometry((SoGeometry)node, gc);
			break;
		case TrimmedCircle:
			renderTrimmedCurve((SoTrimmedCircle)node, gc);
			break;			
		case Group:
			renderGroup((SoGroup)node, gc);
			break;
		case Circle:
			renderCircle((SoCircle)node, gc);
			break;			
		case Text:
			renderText((SoText)node, gc);
			break;
		case Transformation:
			renderTransformation((SoTransformation)node, gc);
			break;
		case Node:
			break;
		}
		
		popSelectionColor(node);
	}
	
	public void renderText(SoText text, Graphics2D gc) throws NoninvertibleTransformException {
		AffineTransform at = gc.getTransform();
		if (text.position.enabled) {
			gc.translate(text.position.origin.x, text.position.origin.y);
			gc.rotate(text.getPosition().rotationAngle);
		}		
		gc.scale(text.scale.x, text.scale.y);
		// gc.setFont(text.getFont());
		gc.drawString(text.getValue(), 0, 0);
		gc.setTransform(at);
	}
	
	public void renderCircle(SoCircle circle, Graphics2D gc) throws NoninvertibleTransformException {
		if (camera.canSee(circle)) {
			gc.setStroke(circle.getStroke());
			gc.draw(circle.getShape());
		}
	}
	
	public void renderTrimmedCurve(SoTrimmedCircle circle, Graphics2D gc) throws NoninvertibleTransformException {
		if (camera.canSee(circle)) {
			AffineTransform at = gc.getTransform();
			if (circle.position.enabled) {
				gc.translate(circle.position.origin.x, circle.position.origin.y);
				gc.rotate(circle.getPosition().rotationAngle);
			}
			// due to the nature of the ARC rendering we have to flip the coord system again
			gc.scale(1.0, -1.0);
			gc.setStroke(circle.getStroke());
			gc.draw(circle.getShape());
			gc.setTransform(at);
		}
	}
	
	public void renderGeometry(SoGeometry shape, Graphics2D gc) throws NoninvertibleTransformException {
		if (camera.canSee(shape)) {
			gc.setStroke(shape.getStroke());
			gc.draw(shape.getShape());
		}
	}
	
	public void renderGroup(SoGroup group, Graphics2D gc) throws NoninvertibleTransformException {
		for (SoNode node : group.getChildren()) {
			renderNode(node, gc);
		}
	}
	
	public void renderTransformation(SoTransformation transform, Graphics2D gc) throws NoninvertibleTransformException {
		AffineTransform at = gc.getTransform();
		if (transform.position.enabled) {
			gc.translate(transform.position.origin.x, transform.position.origin.y);
			gc.rotate(transform.getPosition().rotationAngle);
		}		
		for (SoNode node : transform.getChildren()) {
			renderNode(node, gc);
		}
		gc.setTransform(at);
	}
	
}
