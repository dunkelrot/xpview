package com.basf.xpview.plantviewer.adapter;

import java.util.ArrayList;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.basf.xpview.model.Catalog;
import com.basf.xpview.model.Thing;
import com.basf.xpview.plantviewer.Activator;

public class CatalogAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;

	public CatalogAdapter() {
		this.icon = Activator.getImageDescriptor("icons/package.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		Catalog catalog = (Catalog) o;
		ArrayList<Thing> things = new ArrayList<Thing>();
		things.addAll(catalog.getPlantItems());
		return things.toArray();
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
