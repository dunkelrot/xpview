package org.dexpi.xpview.plantviewer.adapter;

import java.util.ArrayList;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.Plant;
import org.dexpi.xpview.model.PlantSection;

public class PlantAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public PlantAdapter() {
		
	}
	
	@Override
	public Object[] getChildren(Object o) {
		Plant plant = (Plant) o;
		ArrayList<Object> children = new ArrayList<Object>();
		children.add(plant.getCatalogList());
		children.add(plant.getDrawings());
		children.add(plant.getEquipmentList());
		children.add(plant.getInstrumentList());
		children.add(plant.getPipingNetworkList());
		for (PlantSection plantSection : plant.getSections()) {
			children.add(plantSection);
		}
		return children.toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		Plant plant = (Plant) o;
		return plant.getName();
	}

	@Override
	public Object getParent(Object o) {
		Plant plant = (Plant) o;
		return plant.getParent();
	}
	
}
