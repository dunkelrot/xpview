package com.basf.xpview.pidviewer.editors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.basf.xpview.core.Event;
import com.basf.xpview.core.EventListener;
import com.basf.xpview.model.events.EventTypes;
import com.basf.xpview.model.graphics.SoGraphics;
import com.basf.xpview.model.graphics.SoNode;
import com.basf.xpview.pidviewer.utils.Graphics2DRenderer;

/**
 * A thin wrapper around the browser control.
 * 
 * @author Arndt Teinert
 * 
 */
public class PIDEditorViewer implements EventListener {

	protected Canvas canvas;
	protected Composite parent;
	protected SoGraphics renderer;

	protected double factor = 1.0;
	
	protected SoNode node;

	final Graphics2DRenderer rendererG2D = new Graphics2DRenderer();

	public PIDEditorViewer(Composite parent) {
		this.parent = parent;
		this.renderer = new SoGraphics();
	}

	public void init() {
		canvas = new Canvas(parent, SWT.NO_BACKGROUND );

		canvas.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseScrolled(MouseEvent e) {
				factor = factor - (factor * e.count * 0.01);
				double zoom = 1.0 / factor;
				renderer.getCamera().setZoom(zoom);
				canvas.redraw();
			}
		});
		
		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				Point controlSize = ((Control) e.getSource()).getSize();

				if (node != null) {
					
					rendererG2D.prepareRendering(e.gc);
					
					Graphics2D g2d = rendererG2D.getGraphics2D();
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					
					g2d.translate(10, controlSize.y - 10);
					g2d.scale(1.0, -1.0);
					g2d.setPaint(new Color(255,255,255));
					g2d.fillRect(-10, -10, controlSize.x + 10, controlSize.y + 10);

					g2d.setColor(new Color(0,0,0));

					renderer.render(node, g2d);
					rendererG2D.render(e.gc);
				}
			}
		});
		
		com.basf.xpview.core.EventManager.getInstance().registerForEvent(EventTypes.SceneGraphModified, this);
	}

	public void setFocus() {
		parent.setFocus();
	}

	public void setInput(SoNode node) {
		this.node = node;
		canvas.redraw();
	}

	@Override
	public void onEvent(Event event) {
		if (event.getType() == EventTypes.SceneGraphModified) {
			canvas.redraw();
		}
	}

	public void dispose() {
		com.basf.xpview.core.EventManager.getInstance().unregisterForEvent(EventTypes.SceneGraphModified, this);
	}
}
