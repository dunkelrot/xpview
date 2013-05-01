package org.dexpi.xpview.plantviewer.adapter;

import java.util.ArrayList;

import org.dexpi.xpview.model.PlantItem;
import org.dexpi.xpview.model.PlantItemContainer;
import org.dexpi.xpview.model.TextBag;
import org.dexpi.xpview.plantviewer.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class PlantItemAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public PlantItemAdapter() {
		this.icon = Activator.getImageDescriptor("icons/question-white.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		PlantItem plantItem = (PlantItem) o;
		ArrayList<Object> children = new ArrayList<Object>(); 
		if (plantItem instanceof TextBag) {
			children.add(((TextBag)plantItem).getTextList());
		}
		if (plantItem instanceof PlantItemContainer) {
			children.addAll(((PlantItemContainer)plantItem).getPlantItems());
		}
		return children.toArray();
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
