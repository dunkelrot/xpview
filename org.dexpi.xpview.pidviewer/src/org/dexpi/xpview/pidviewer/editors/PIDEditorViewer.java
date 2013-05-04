package org.dexpi.xpview.pidviewer.editors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import org.dexpi.xpview.core.Event;
import org.dexpi.xpview.core.EventListener;
import org.dexpi.xpview.model.Thing;
import org.dexpi.xpview.model.events.EventTypes;
import org.dexpi.xpview.model.graphics.BoundingBox;
import org.dexpi.xpview.model.graphics.NodeUtils;
import org.dexpi.xpview.model.graphics.RepresentationManager;
import org.dexpi.xpview.model.graphics.SoGroup;
import org.dexpi.xpview.model.graphics.SoNode;
import org.dexpi.xpview.model.graphics.SoSelectionManager;
import org.dexpi.xpview.model.graphics.j2d.Graphics;
import org.dexpi.xpview.pidviewer.utils.Graphics2DRenderer;
import org.dexpi.xpview.pidviewer.utils.ThingSelection;

/**
 * A thin wrapper around the browser control.
 * 
 * @author Arndt Teinert
 * 
 */
public class PIDEditorViewer implements EventListener, ISelectionChangedListener, ISelectionListener,
		ISelectionProvider {

	private static Logger log = Logger.getLogger(PIDEditorViewer.class);

	protected ArrayList<ISelectionChangedListener> selectionListeners;

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
		this.initialMovePositon = new Point(0, 0);
		this.selectionManager = new SoSelectionManager();
		this.selectionListeners = new ArrayList<ISelectionChangedListener>();
	}

	public void init() {
		canvas = new Canvas(parent, SWT.NO_BACKGROUND);

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
					g2d.setPaint(new Color(255, 255, 255));
					g2d.setColor(new Color(0, 0, 0));

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

				try {
					Point2D worldPt = renderer.getCamera().screenToWorld(new Point2D.Double(e.x, e.y));
					System.out.println("World coords: " + worldPt.getX() + ":" + worldPt.getY());
				} catch (NoninvertibleTransformException ex) {

				}

				if (e.button == 1) {
					try {
						Point2D screenPt = new Point2D.Double(e.x, e.y);
						Point2D worldPt = renderer.getCamera().screenToWorld(screenPt);
						ArrayList<SoNode> nodes = new ArrayList<SoNode>();
						NodeUtils.getNodesAt((SoGroup) node, new AffineTransform(), worldPt.getX(), worldPt.getY(),
								nodes);
						System.out.println(nodes.size());

						if (nodes.size() > 0) {
							selectionManager.clear();
							selectionManager.addNode(nodes.get(0));
							canvas.redraw();

							fireSelectionChanged(new SelectionChangedEvent(PIDEditorViewer.this, new ThingSelection(
									selectionManager, RepresentationManager.getInstance())));
						} else {
							if (selectionManager.size() != 0) {
								selectionManager.clear();
								canvas.redraw();
							}
						}
					} catch (Exception ex) {
						// TODO
					}
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {

			}
		});

		canvas.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				Rectangle rect = canvas.getBounds();
				renderer.getCamera().setHeight(rect.height);
				renderer.getCamera().setWidth(rect.width);

				if (firstResizeEvent) {
					showAll();
					firstResizeEvent = false;
				}
			}
		});

		org.dexpi.xpview.core.EventManager.getInstance().registerForEvent(EventTypes.SceneGraphModified, this);
	}

	public void setFocus() {
		parent.setFocus();
	}

	public void setInput(SoNode node) {
		this.node = node;
		BoundingBox bBox = node.getBoundingBox();
		log.debug("Overall BoundingBox: " + bBox);
		renderer.getCamera().lookAt(bBox.getCenter());
		renderer.prepare(node);
		canvas.redraw();
	}

	@Override
	public void onEvent(Event event) {
		if (event.getType() == EventTypes.SceneGraphModified) {
			canvas.redraw();
		}
	}

	public void dispose() {
		selectionManager.clear();
		org.dexpi.xpview.core.EventManager.getInstance().unregisterForEvent(EventTypes.SceneGraphModified, this);
	}

	public void setZoom(double zoom) {
		BoundingBox bBox = node.getBoundingBox();
		renderer.getCamera().lookAt(bBox.getCenter());
		renderer.getCamera().setZoom(zoom);
		factor = 1.0 / renderer.getCamera().getZoom();
		canvas.redraw();
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

	public void zoomTo(SoNode node) {
		NodeUtils utils = new NodeUtils();

		BoundingBox bBox = node.getBoundingBox().transformed(utils.getOverallTransform(node));
		bBox.inflate(1.1);
		renderer.getCamera().lookAt(bBox.getCenter());

		Point controlSize = canvas.getSize();
		double factorX = controlSize.x / bBox.getWidth();
		double factorY = controlSize.y / bBox.getHeight();

		double zoomFactor = 1.0;
		if (factorX > factorY) {
			zoomFactor = factorY;
		} else {
			zoomFactor = factorX;
		}

		if (zoomFactor > 4.0) {
			zoomFactor = 4.0;
		}

		renderer.getCamera().setZoom(zoomFactor);
		factor = 1.0 / renderer.getCamera().getZoom();
		canvas.redraw();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		setSelection(selection);
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (selectionListeners.contains(listener) == false) {
			selectionListeners.add(listener);
		}
	}

	@Override
	public ISelection getSelection() {
		return new ThingSelection(selectionManager, RepresentationManager.getInstance());
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		selectionManager.clear();

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structSel = (IStructuredSelection) selection;
			if (structSel.isEmpty() == false) {
				Object obj = structSel.getFirstElement();
				if (obj instanceof Thing) {
					Thing input = ((Thing) obj);
					SoNode node = RepresentationManager.getInstance().getNode(input);
					if (node != null) {
						selectionManager.addNode(node);
					}
				}
			}
		}

		canvas.redraw();
	}

	public void fireSelectionChanged(SelectionChangedEvent event) {
		for (ISelectionChangedListener li : selectionListeners) {
			li.selectionChanged(event);
		}
	}

}
