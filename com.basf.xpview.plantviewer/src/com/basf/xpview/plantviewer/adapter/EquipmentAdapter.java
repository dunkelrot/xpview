package com.basf.xpview.plantviewer.adapter;

import java.util.ArrayList;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.basf.xpview.model.Equipment;
import com.basf.xpview.model.PlantItem;
import com.basf.xpview.model.PropertyList;
import com.basf.xpview.plantviewer.Activator;

public class EquipmentAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public EquipmentAdapter() {
		this.icon = Activator.getImageDescriptor("icons/brick.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		Equipment equipment = (Equipment) o;
		ArrayList<PlantItem> children = new ArrayList<PlantItem>();
		children.addAll(equipment.getSubEquipments());
		children.addAll(equipment.getNozzles());
		return children.toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		Equipment equipment = (Equipment) o;
		String label = equipment.getName(); 
		PropertyList defaultProps = equipment.getPropertyData().getProperyList("Default");
		if (defaultProps != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(defaultProps.getPropertyValue("TagName"));
			buffer.append(" ");
			buffer.append(defaultProps.getPropertyValue("ComponentClass"));
			buffer.append(" ");
			buffer.append(defaultProps.getPropertyValue("ComponentName"));
			label = buffer.toString().trim();
		}
		return label;
	}

	@Override
	public Object getParent(Object o) {
		Equipment equipment = (Equipment) o;
		return equipment.getContainer();
	}

}
