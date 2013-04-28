package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.PipingNetworkList;
import org.dexpi.xpview.plantviewer.Activator;

public class PipingNetworkListAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public PipingNetworkListAdapter() {
		this.icon = Activator.getImageDescriptor("icons/package.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		PipingNetworkList pipingNetworkList = (PipingNetworkList) o;
		return pipingNetworkList.getPipingNetworks().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		return "PipingNetworks";
	}

	@Override
	public Object getParent(Object o) {
		PipingNetworkList pipingNetworkList = (PipingNetworkList) o;
		return pipingNetworkList.getPlant();
	}

}
