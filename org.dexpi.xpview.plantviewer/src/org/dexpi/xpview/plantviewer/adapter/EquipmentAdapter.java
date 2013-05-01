package org.dexpi.xpview.plantviewer.adapter;

import java.util.ArrayList;

import org.dexpi.xpview.model.Equipment;
import org.dexpi.xpview.model.PropertyList;
import org.dexpi.xpview.plantviewer.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class EquipmentAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public EquipmentAdapter() {
		this.icon = Activator.getImageDescriptor("icons/brick.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		Equipment equipment = (Equipment) o;
		ArrayList<Object> children = new ArrayList<Object>();
		children.add(equipment.getTextList());
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
