package org.dexpi.xpview.plantviewer.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.CatalogList;
import org.dexpi.xpview.plantviewer.Activator;

public class CatalogListAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;

	public CatalogListAdapter() {
		this.icon = Activator.getImageDescriptor("icons/package.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		CatalogList catalogList = (CatalogList) o;
		return catalogList.getCatalogs().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		return "Catalogs";
	}

	@Override
	public Object getParent(Object o) {
		CatalogList catalogList = (CatalogList) o;
		return catalogList.getParent();
	}

}
