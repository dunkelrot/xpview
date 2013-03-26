package com.basf.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.basf.xpview.model.Equipment;

public class EquipmentAdapter implements IWorkbenchAdapter {

	@Override
	public Object[] getChildren(Object o) {
		// Equipment equipment = (Equipment) o;
		return new Object[0];
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		Equipment equipment = (Equipment) o;
		return equipment.getName();
	}

	@Override
	public Object getParent(Object o) {
		Equipment equipment = (Equipment) o;
		return equipment.getEquipmentList();
	}

}
