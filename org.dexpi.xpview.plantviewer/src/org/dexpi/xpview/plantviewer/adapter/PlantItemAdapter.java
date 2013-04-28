package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.PlantItem;
import org.dexpi.xpview.model.PlantItemContainer;
import org.dexpi.xpview.plantviewer.Activator;

public class PlantItemAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public PlantItemAdapter() {
		this.icon = Activator.getImageDescriptor("icons/question-white.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		PlantItem plantItem = (PlantItem) o;
		if (plantItem instanceof PlantItemContainer) {
			return ((PlantItemContainer)plantItem).getPlantItems().toArray();
		} else {
			return new Object[0];
		}
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		PlantItem plantItem = (PlantItem) o;
		return LabelUtils.getPlantItemLabel("PlantItem", plantItem);
	}

	@Override
	public Object getParent(Object o) {
		PlantItem plantItem = (PlantItem) o;
		return plantItem.getContainer();
	}

}
