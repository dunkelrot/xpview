package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.Nozzle;
import org.dexpi.xpview.model.PropertyList;
import org.dexpi.xpview.plantviewer.Activator;

public class NozzleAdapter implements IWorkbenchAdapter {
	
	protected ImageDescriptor icon;
	
	public NozzleAdapter() {
		this.icon = Activator.getImageDescriptor("icons/connect.png"); //$NON-NLS-1$
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
		Nozzle nozzle = (Nozzle) o;
		String label = nozzle.getName(); 
		PropertyList defaultProps = nozzle.getPropertyData().getProperyList("Default");
		if (defaultProps != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(defaultProps.getPropertyValue("TagName") + " ");
			buffer.append(defaultProps.getPropertyValue("ComponentClass") + " ");
			buffer.append(defaultProps.getPropertyValue("ComponentName"));
			label = buffer.toString().trim();
		}
		return label;
	}

	@Override
	public Object getParent(Object o) {
		Nozzle nozzle = (Nozzle) o;
		return nozzle.getContainer();
	}
	
}
