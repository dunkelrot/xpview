package com.basf.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.basf.xpview.model.Drawing;
import com.basf.xpview.plantviewer.Activator;

public class DrawingAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public DrawingAdapter() {
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
		Drawing drawing = (Drawing) o;
		return drawing.getPropertyValue("Name");
	}

	@Override
	public Object getParent(Object o) {
		Drawing drawing = (Drawing) o;
		return drawing.getDrawingList();
	}

}
