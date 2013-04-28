package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.PropertyList;

public class PropertyListAdapter implements IWorkbenchAdapter {

	@Override
	public Object[] getChildren(Object o) {
		PropertyList propertyList = (PropertyList) o;
		return propertyList.getProperties().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		PropertyList propertyList = (PropertyList) o;
		return propertyList.getName();
	}

	@Override
	public Object getParent(Object o) {
		PropertyList propertyList = (PropertyList) o;
		return propertyList.getPropertyData();
	}

}
