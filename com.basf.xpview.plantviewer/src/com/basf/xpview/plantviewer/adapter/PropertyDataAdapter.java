package com.basf.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.basf.xpview.model.PropertyData;

public class PropertyDataAdapter implements IWorkbenchAdapter  {

	@Override
	public Object[] getChildren(Object o) {
		PropertyData propertyData = (PropertyData) o;
		return propertyData.getProperyLists().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		return "Properties";
	}

	@Override
	public Object getParent(Object o) {
		return null;
	}

}
