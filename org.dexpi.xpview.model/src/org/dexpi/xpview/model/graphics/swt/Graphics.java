package org.dexpi.xpview.model.graphics.swt;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;

import org.dexpi.xpview.model.graphics.Camera;
import org.dexpi.xpview.model.graphics.SoCircle;
import org.dexpi.xpview.model.graphics.SoGeometry;
import org.dexpi.xpview.model.graphics.SoGroup;
import org.dexpi.xpview.model.graphics.SoMaterial;
import org.dexpi.xpview.model.graphics.SoNode;
import org.dexpi.xpview.model.graphics.SoText;
import org.dexpi.xpview.model.graphics.SoTransformation;
import org.dexpi.xpview.model.graphics.SoTrimmedCircle;
import org.dexpi.xpview.model.graphics.TransformedStroke;


public class Graphics {

	protected Camera camera;
	protected Deque<SoMaterial> colorStack;
	protected ArrayList<Transform> transformStack;
	protected int transformIndex;
	protected boolean selectionColorActive;
	
	public Graphics(Device device) {
		this.camera = new Camera();
		this.colorStack = new ArrayDeque<SoMaterial>(64);
		SoMaterial defaultMaterial = new SoMaterial(null, 0, "Default");
		defaultMaterial.init(0, 0, 0, 255);
		this.colorStack.push(defaultMaterial);
		
		for (int ii = 0; ii < 64; ii++) {
			transformStack.add(new Transform(device));
		}
		transformIndex = 0;
		this.selectionColorActive = false;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void render(SoNode node, GC gc) {
		transformIndex += 1;
		Transform transform = transformStack.get(transformIndex);
		gc.getTransform(transform);
		
		transform.scale((float)camera.getZoom(), (float)camera.getZoom());
		transform.translate((float)- camera.getEye().x,(float) - camera.getEye().y);
		try {
			renderNode(node, gc);
		} catch (NoninvertibleTransformException ex) {
			System.out.println(ex.getMessage());
		}
		transformIndex -= 1;
		gc.setTransform(transform);
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
	
	public void renderNode(SoNode node, GC gc) throws NoninvertibleTransformException {

		pushSelectionColor(node);
		// gc.setForeground(colorStack.peek().getColor());
		
		switch (node.getType()) {
		case PolyLine:
		case Shape:
		case Line:
			// renderGeometry((SoGeometry)node, gc);
			break;
		case TrimmedCircle:
			// renderTrimmedCurve((SoTrimmedCircle)node, gc);
			break;			
		case Group:
			// renderGroup((SoGroup)node, gc);
			break;
		case Circle:
			renderCircle((SoCircle)node, gc);
			break;			
		case Text:
			renderText((SoText)node, gc);
			break;
		case Transformation:
			// renderTransformation((SoTransformation)node, gc);
			break;
		case Node:
			break;
		}
		
		popSelectionColor(node);
	}
	
	public void renderText(SoText text, GC gc) throws NoninvertibleTransformException {
		// AffineTransform at = gc.getTransform();
		// gc.scale(text.scale.x, text.scale.y);
		// gc.setFont(text.getFont());
		gc.drawString(text.getValue(), 0, 0);
		// gc.setTransform(at);
	}
	
	public void renderCircle(SoCircle circle, GC gc) throws NoninvertibleTransformException {
		if (camera.canSee(circle)) {
			// gc.setStroke(new TransformedStroke(circle.getStroke(), gc.getTransform()));
			// gc.drawOval(circle.getRadius());
		}
	}
	
	public void renderTrimmedCurve(SoTrimmedCircle circle, Graphics2D gc) throws NoninvertibleTransformException {
		if (camera.canSee(circle)) {
			AffineTransform at = gc.getTransform();
			// due to the nature of the ARC rendering we have to flip the coord system again
			gc.scale(1.0, -1.0);
			gc.setStroke(new TransformedStroke(circle.getStroke(), gc.getTransform()));
			gc.draw(circle.getShape());
			gc.setTransform(at);
		}
	}
	
	public void renderGeometry(SoGeometry shape, Graphics2D gc) throws NoninvertibleTransformException {
		if (camera.canSee(shape)) {
			gc.draw(shape.getShape());
		}
	}
	
	public void renderGroup(SoGroup group, Graphics2D gc) throws NoninvertibleTransformException {
		for (SoNode node : group.getChildren()) {
			// renderNode(node, gc);
		}
	}
	
	public void renderTransformation(SoTransformation transform, Graphics2D gc) throws NoninvertibleTransformException {
		AffineTransform at = gc.getTransform();
		if (transform.position.enabled) {
			gc.translate(transform.position.origin.x, transform.position.origin.y);
			gc.rotate(transform.getPosition().rotationAngle);
		}		
		for (SoNode node : transform.getChildren()) {
			// renderNode(node, gc);
		}
		gc.setTransform(at);
	}
	
}
