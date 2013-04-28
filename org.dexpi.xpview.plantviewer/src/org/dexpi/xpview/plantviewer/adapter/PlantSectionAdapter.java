package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.PlantSection;

public class PlantSectionAdapter implements IWorkbenchAdapter {

	@Override
	public Object[] getChildren(Object o) {
		PlantSection plantSection = (PlantSection) o;
		return plantSection.getSections().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		PlantSection plantSection = (PlantSection) o;
		return plantSection.getName();
	}

	@Override
	public Object getParent(Object o) {
		PlantSection plantSection = (PlantSection) o;
		return plantSection.getParent();
	}

}
