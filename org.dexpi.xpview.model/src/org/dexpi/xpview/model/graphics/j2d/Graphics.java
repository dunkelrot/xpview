package org.dexpi.xpview.model.graphics.j2d;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayDeque;
import java.util.Deque;

import org.dexpi.xpview.model.graphics.Camera;
import org.dexpi.xpview.model.graphics.SoCircle;
import org.dexpi.xpview.model.graphics.SoGeometry;
import org.dexpi.xpview.model.graphics.SoGroup;
import org.dexpi.xpview.model.graphics.SoMaterial;
import org.dexpi.xpview.model.graphics.SoNode;
import org.dexpi.xpview.model.graphics.SoPoint;
import org.dexpi.xpview.model.graphics.SoText;
import org.dexpi.xpview.model.graphics.SoTransformation;
import org.dexpi.xpview.model.graphics.SoTrimmedCircle;


public class Graphics {

	protected Camera camera;
	protected Deque<SoMaterial> colorStack;
	protected boolean selectionColorActive;
	protected FontFactory fontFactory;
	
	public Graphics() {
		this.camera = new Camera();
		this.colorStack = new ArrayDeque<SoMaterial>(64);
		SoMaterial defaultMaterial = new SoMaterial(null, 0, "Default");
		defaultMaterial.init(0, 0, 0, 255);
		this.colorStack.push(defaultMaterial);
		this.fontFactory = new FontFactory();
		this.selectionColorActive = false;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void prepare(SoNode node) {
		FontGenerator fontGenerator = new FontGenerator(fontFactory);
		fontGenerator.createFonts(node);
	}
	
	public void render(SoNode node, Graphics2D gc) {
		AffineTransform saveTA = gc.getTransform();
		camera.transform(gc);
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
	
	protected void pushColor(SoMaterial material) {
		if (selectionColorActive == false) {
			colorStack.push(material);
		}
	}

	protected void popColor() {
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
		case Point:
			renderPoint((SoPoint)node, gc);
			break;
		case Node:
			break;
		case NodeT:
			break;
		}
		
		popSelectionColor(node);
	}
	
	public void renderText(SoText text, Graphics2D gc) throws NoninvertibleTransformException {
		AffineTransform at = gc.getTransform();

		// due to the nature of the TEXT rendering we have to flip the coordinate system again
		gc.scale(1.0, -1.0);
		
		gc.setFont((Font) text.getFontData().getFont());
		int index = 0;
		for (String line : text.getLines()) {
			gc.drawString(line, 0, (int)(index * text.getHeight()));
			index = index + 1;
		}
		gc.setTransform(at);
	}
	
	public void renderCircle(SoCircle circle, Graphics2D gc) throws NoninvertibleTransformException {
		if (camera.canSee(circle)) {
			gc.setStroke(circle.getStroke());
			gc.draw(circle.getShape());
		}
	}
	
	public void renderPoint(SoPoint point, Graphics2D gc) throws NoninvertibleTransformException {
		if (camera.canSee(point)) {
			gc.setStroke(point.getStroke());
			gc.draw(point.getShape());
		}
	}
	
	public void renderTrimmedCurve(SoTrimmedCircle circle, Graphics2D gc) throws NoninvertibleTransformException {
		if (camera.canSee(circle)) {
			AffineTransform at = gc.getTransform();
			if (circle.position.enabled) {
				gc.translate(circle.position.origin.x, circle.position.origin.y);
				gc.rotate(circle.getPosition().rotationAngle);
			}
			// due to the nature of the ARC rendering we have to flip the coordinate system again
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
		if (group.getMaterial() != null) {
			pushColor(group.getMaterial());
		}
		for (SoNode node : group.getChildren()) {
			renderNode(node, gc);
		}
		if (group.getMaterial() != null) {
			popColor();
		}
	}
	
	public void renderTransformation(SoTransformation transform, Graphics2D gc) throws NoninvertibleTransformException {

		// gc.draw(transform.getBoundingBox().getRectangle());
		
		if (transform.getMaterial() != null) {
			pushColor(transform.getMaterial());
		}
		
		AffineTransform at = gc.getTransform();
		if (transform.position.enabled) {
			gc.translate(transform.position.origin.x, transform.position.origin.y);
			gc.rotate(transform.getPosition().rotationAngle);
		}		
		
		for (SoNode node : transform.getChildren()) {
			renderNode(node, gc);
		}
		gc.setTransform(at);
		
		if (transform.getMaterial() != null) {
			popColor();
		}
	}
	
}
