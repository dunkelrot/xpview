package org.dexpi.xpview.plantviewer.adapter;

import org.dexpi.xpview.model.InstrumentList;
import org.dexpi.xpview.plantviewer.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class InstrumentListAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;

	public InstrumentListAdapter() {
		this.icon = Activator.getImageDescriptor("icons/package.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		InstrumentList instrumentList = (InstrumentList) o;
		return instrumentList.getPlantItems().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		return "Instruments";
	}

	@Override
	public Object getParent(Object o) {
		InstrumentList instrumentList = (InstrumentList) o;
		return instrumentList.getPlant();
	}

}
