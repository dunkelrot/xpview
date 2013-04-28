package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.PipingNetwork;
import org.dexpi.xpview.plantviewer.Activator;

public class PipingNetworkAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public PipingNetworkAdapter() {
		this.icon = Activator.getImageDescriptor("icons/node.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		PipingNetwork pipingNetwork = (PipingNetwork) o;
		return pipingNetwork.getSegments().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		PipingNetwork pipingNetwork = (PipingNetwork) o;
		return LabelUtils.getPlantItemLabel("PipingNetwork", pipingNetwork);
	}

	@Override
	public Object getParent(Object o) {
		PipingNetwork pipingNetwork = (PipingNetwork) o;
		return pipingNetwork.getContainer();
	}

}
