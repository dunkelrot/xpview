package com.basf.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.basf.xpview.model.Nozzle;
import com.basf.xpview.plantviewer.Activator;

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
		return nozzle.getName();
	}

	@Override
	public Object getParent(Object o) {
		Nozzle nozzle = (Nozzle) o;
		return nozzle.getParent();
	}
	
}
