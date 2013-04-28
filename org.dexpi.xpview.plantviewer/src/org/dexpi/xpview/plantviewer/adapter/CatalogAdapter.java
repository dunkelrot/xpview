package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.Catalog;
import org.dexpi.xpview.plantviewer.Activator;

public class CatalogAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;

	public CatalogAdapter() {
		this.icon = Activator.getImageDescriptor("icons/drawer.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		Catalog catalog = (Catalog) o;
		return catalog.getPlantItems().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		Catalog catalog = (Catalog) o;
		return catalog.getName(); 
	}

	@Override
	public Object getParent(Object o) {
		Catalog catalog = (Catalog) o;
		return catalog.getParent();
	}

}
