package com.basf.xpview.pidviewer.editors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import com.basf.xpview.core.Event;
import com.basf.xpview.core.EventListener;
import com.basf.xpview.model.Thing;
import com.basf.xpview.model.events.EventTypes;
import com.basf.xpview.model.graphics.BoundingBox;
import com.basf.xpview.model.graphics.RepresentationManager;
import com.basf.xpview.model.graphics.SoNode;
import com.basf.xpview.model.graphics.SoSelectionManager;
import com.basf.xpview.model.graphics.j2d.Graphics;
import com.basf.xpview.pidviewer.utils.Graphics2DRenderer;

/**
 * A thin wrapper around the browser control.
 * 
 * @author Arndt Teinert
 * 
 */
public class PIDEditorViewer implements EventListener, ISelectionChangedListener, ISelectionListener {

	private static Logger log = Logger.getLogger(PIDEditorViewer.class);
	
	protected Canvas canvas;
	protected Composite parent;
	protected Graphics renderer;
	protected SoSelectionManager selectionManager;
	
	protected double factor = 1.0;
	protected boolean firstResizeEvent = true;
	
	protected boolean isMoving;
	protected Point initialMovePositon;
	
	protected SoNode node;

	final Graphics2DRenderer rendererG2D = new Graphics2DRenderer();

	public PIDEditorViewer(Composite parent) {
		this.parent = parent;
		this.renderer = new Graphics();
		this.initialMovePositon = new Point(0,0);
		this.selectionManager = new SoSelectionManager();
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
					g2d.fillRect(0, 0, controlSize.x, controlSize.y);
					g2d.setPaint(new Color(255,255,255));
					g2d.setColor(new Color(0,0,0));
					
					g2d.translate(controlSize.x * 0.5, controlSize.y * 0.5);
					g2d.scale(1.0,  -1.0);
					
					renderer.render(node, g2d);
					rendererG2D.render(e.gc);
				}
			}
		});
		
		canvas.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				if (isMoving) {
					int diffX = (e.x - initialMovePositon.x) * -1;
					int diffY = (e.y - initialMovePositon.y);
					
					renderer.getCamera().moveByZoomed(diffX, diffY);
					
					initialMovePositon.x = e.x;
					initialMovePositon.y = e.y;
					
					canvas.redraw();
				}
			}
		});
		
		canvas.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				isMoving = false;
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				initialMovePositon.x = e.x;
				initialMovePositon.y = e.y;
				isMoving = true;
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
			}
		});
		
		canvas.addListener(SWT.Resize,  new Listener () {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				if (firstResizeEvent) {
					showAll();
					firstResizeEvent = false;
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
		BoundingBox bBox = node.getBoundingBox();
		log.debug("Overall BoundingBox: " + bBox);
		renderer.getCamera().lookAt(bBox.getCenter());
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
	
	public void showAll() {
		BoundingBox bBox = node.getBoundingBox();
		renderer.getCamera().lookAt(bBox.getCenter());
		
		Point controlSize = canvas.getSize();
		double factorX = controlSize.x / bBox.getWidth();
		double factorY = controlSize.y / bBox.getHeight();
		if (factorX > factorY) {
			renderer.getCamera().setZoom(factorY);
		} else {
			renderer.getCamera().setZoom(factorX);
		}

		factor = 1.0 / renderer.getCamera().getZoom();
		canvas.redraw();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {

		selectionManager.clear();
		
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structSel = (IStructuredSelection) selection;
			Object obj = structSel.getFirstElement();
			if (obj instanceof Thing) {
				Thing input = ((Thing) obj);
				SoNode node = RepresentationManager.getInstance().getNode(input);
				if (node != null) {
					selectionManager.addNode(node);	
				}
			}
		}

		canvas.redraw();
	}

	
}
