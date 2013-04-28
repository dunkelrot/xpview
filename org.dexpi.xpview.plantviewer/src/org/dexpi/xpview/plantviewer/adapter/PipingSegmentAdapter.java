package org.dexpi.xpview.plantviewer.adapter;

import java.util.ArrayList;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.PipingSegment;
import org.dexpi.xpview.plantviewer.Activator;

public class PipingSegmentAdapter implements IWorkbenchAdapter {
	
	protected ImageDescriptor icon;
	
	public PipingSegmentAdapter() {
		this.icon = Activator.getImageDescriptor("icons/node-select-next.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		PipingSegment pipingSegment = (PipingSegment) o;
		ArrayList<Object> children = new ArrayList<Object>();
		children.addAll(pipingSegment.getPlantItems());
		children.addAll(pipingSegment.getAnnotations());
		return children.toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		PipingSegment pipingSegment = (PipingSegment) o;
		return LabelUtils.getPlantItemLabel("Segment", pipingSegment);
	}

	@Override
	public Object getParent(Object o) {
		PipingSegment pipingSegment = (PipingSegment) o;
		return pipingSegment.getContainer();
	}

}
