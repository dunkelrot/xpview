package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.PipingComponent;
import org.dexpi.xpview.plantviewer.Activator;

public class PipingComponentAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public PipingComponentAdapter() {
		this.icon = Activator.getImageDescriptor("icons/gear.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		PipingComponent pipingComponent = (PipingComponent) o;
		return pipingComponent.getPipingComponents().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		PipingComponent pipingComponent = (PipingComponent) o;
		return LabelUtils.getPlantItemLabel("Component", pipingComponent);
	}

	@Override
	public Object getParent(Object o) {
		PipingComponent pipingComponent = (PipingComponent) o;
		return pipingComponent.getContainer();
	}

}
