package org.dexpi.xpview.plantviewer.adapter;

import java.util.ArrayList;

import org.dexpi.xpview.model.Instrument;
import org.dexpi.xpview.model.PropertyList;
import org.dexpi.xpview.plantviewer.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class InstrumentAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public InstrumentAdapter() {
		this.icon = Activator.getImageDescriptor("icons/brick.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		Instrument instrument = (Instrument) o;
		ArrayList<Object> children = new ArrayList<Object>();
		children.add(instrument.getTextList());
		children.addAll(instrument.getPlantItems());
		return children.toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		Instrument instrument = (Instrument) o;
		String label = instrument.getName(); 
		PropertyList defaultProps = instrument.getPropertyData().getProperyList("Default");
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
		Instrument instrument = (Instrument) o;
		return instrument.getContainer();
	}

}
