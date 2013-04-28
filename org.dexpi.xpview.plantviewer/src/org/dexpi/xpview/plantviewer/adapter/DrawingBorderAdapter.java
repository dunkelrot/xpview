package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.DrawingBorder;
import org.dexpi.xpview.plantviewer.Activator;

public class DrawingBorderAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public DrawingBorderAdapter() {
		this.icon = Activator.getImageDescriptor("icons/page.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		return new Object[0];
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		DrawingBorder drawingBorder = (DrawingBorder) o;
		return drawingBorder.getName();
	}

	@Override
	public Object getParent(Object o) {
		DrawingBorder drawingBorder = (DrawingBorder) o;
		return drawingBorder.getDrawing();
	}

}
