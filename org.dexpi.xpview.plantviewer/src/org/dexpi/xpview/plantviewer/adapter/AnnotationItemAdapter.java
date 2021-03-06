package org.dexpi.xpview.plantviewer.adapter;

import java.util.ArrayList;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.AnnotationItem;
import org.dexpi.xpview.plantviewer.Activator;

public class AnnotationItemAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public AnnotationItemAdapter() {
		this.icon = Activator.getImageDescriptor("icons/tag.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		AnnotationItem annotationItem = (AnnotationItem) o;
		ArrayList<Object> children = new ArrayList<Object>(); 
		children.add(annotationItem.getTextList());
		return children.toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		AnnotationItem annotationItem = (AnnotationItem) o;
		return annotationItem.getName();
	}

	@Override
	public Object getParent(Object o) {
		AnnotationItem annotationItem = (AnnotationItem) o;
		return annotationItem.getContainer();
	}

}
