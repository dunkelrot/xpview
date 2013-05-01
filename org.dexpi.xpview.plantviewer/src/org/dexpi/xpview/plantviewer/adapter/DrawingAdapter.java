package org.dexpi.xpview.plantviewer.adapter;

import java.util.ArrayList;

import org.dexpi.xpview.model.Drawing;
import org.dexpi.xpview.plantviewer.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class DrawingAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public DrawingAdapter() {
		this.icon = Activator.getImageDescriptor("icons/document-medium.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		Drawing drawing = (Drawing) o;
		
		ArrayList<Object> children = new ArrayList<Object>(); 
		children.add(drawing.getTextList());
		children.addAll(drawing.getAnnotations());
		if (drawing.getBorder() != null) {
			children.add(drawing.getBorder());
		}
		return children.toArray();
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
