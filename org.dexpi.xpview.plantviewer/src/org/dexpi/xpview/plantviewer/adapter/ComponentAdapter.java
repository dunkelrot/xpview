package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.Component;
import org.dexpi.xpview.plantviewer.Activator;

public class ComponentAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public ComponentAdapter() {
		this.icon = Activator.getImageDescriptor("icons/cog.png"); //$NON-NLS-1$
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
		Component component = (Component) o;
		return component.getName();
	}

	@Override
	public Object getParent(Object o) {
		Component component = (Component) o;
		return component.getContainer();
	}
	
}
