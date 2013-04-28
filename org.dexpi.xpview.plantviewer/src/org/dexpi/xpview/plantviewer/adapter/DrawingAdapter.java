package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.Drawing;
import org.dexpi.xpview.model.DrawingBorder;
import org.dexpi.xpview.plantviewer.Activator;

public class DrawingAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public DrawingAdapter() {
		this.icon = Activator.getImageDescriptor("icons/document-medium.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		Drawing drawing = (Drawing) o;
		DrawingBorder[] border = null;
		if (drawing.getBorder() != null) {
			border = new DrawingBorder[1];
			border[0] = drawing.getBorder();
		} else {
			border = new DrawingBorder[0];
		}
		return border;
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		Drawing drawing = (Drawing) o;
		return drawing.getPropertyData().getPropertyValue("Name");
	}

	@Override
	public Object getParent(Object o) {
		Drawing drawing = (Drawing) o;
		return drawing.getDrawingList();
	}

}
