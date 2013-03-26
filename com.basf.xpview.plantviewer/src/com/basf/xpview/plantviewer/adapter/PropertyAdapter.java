package com.basf.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.basf.xpview.model.Property;

public class PropertyAdapter extends LabelProvider implements IWorkbenchAdapter, ITableLabelProvider {

	@Override
	public Object[] getChildren(Object o) {
		return new Object[0];
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		Property property = (Property) o;
		return property.getName();
	}

	@Override
	public Object getParent(Object o) {
		Property property = (Property) o;
		return property.getPropertyList();
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Property property = (Property) element;
		String text = "";

		if (columnIndex == 0) {
			text = property.getName();
		} else if (columnIndex == 1) {
			text = property.getValue();
		} else if (columnIndex == 2) {
			text = property.getFormat();
		} else if (columnIndex == 3) {
			text = property.getURI();
		}
		return text;
	}

}