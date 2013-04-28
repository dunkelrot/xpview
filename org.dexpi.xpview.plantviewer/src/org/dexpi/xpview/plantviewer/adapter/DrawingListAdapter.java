package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.DrawingList;
import org.dexpi.xpview.plantviewer.Activator;

public class DrawingListAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public DrawingListAdapter() {
		this.icon = Activator.getImageDescriptor("icons/documents-stack.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		DrawingList drawingList = (DrawingList) o;
		return drawingList.getDrawings().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		return "Drawings";
	}

	@Override
	public Object getParent(Object o) {
		DrawingList drawingList = (DrawingList) o;
		return drawingList.getPlantSection();
	}

}
