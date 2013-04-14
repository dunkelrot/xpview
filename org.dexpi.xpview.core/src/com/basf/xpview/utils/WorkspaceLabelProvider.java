package com.basf.xpview.utils;

import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class WorkspaceLabelProvider extends LabelProvider implements ITableLabelProvider {

	protected HashMap<ImageDescriptor, Image> imageCache;

	protected AdapterFactory factory;

	public WorkspaceLabelProvider(AdapterFactory factory) {
		this.factory = factory;
		this.imageCache = new HashMap<ImageDescriptor, Image>();
	}

	@Override
	public Image getColumnImage(Object element, int column) {
		Image result = null;
		if (column == 0) {
			IWorkbenchAdapter adapter = (IWorkbenchAdapter) factory.getAdapter(element, IWorkbenchAdapter.class);
			if (adapter != null) {
				ImageDescriptor imgDesc = adapter.getImageDescriptor(element);
				if (imgDesc != null) {
					result = imageCache.get(imgDesc);
					if (result == null) {
						result = imgDesc.createImage();
						imageCache.put(imgDesc, result);
					}
				}
			}
		}
		return result;
	}

	@Override
	public String getColumnText(Object element, int column) {
		String result = "";
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) factory.getAdapter(element, IWorkbenchAdapter.class);
		if (adapter != null) {
			if (column == 0) {
				result = adapter.getLabel(element);
			} else {
				if (adapter instanceof ITableLabelProvider) {
					result = ((ITableLabelProvider) adapter).getColumnText(element, column);
				}
			}
		}
		return result;
	}

	@Override
	public String getText(Object element) {
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) factory.getAdapter(element, IWorkbenchAdapter.class);
		if (adapter != null) {
			return adapter.getLabel(element);
		}
		return element.toString();
	}

	@Override
	public void dispose() {
		super.dispose();

		for (Image img : imageCache.values()) {
			img.dispose();
		}
	}

	@Override
	public Image getImage(Object element) {
		return getColumnImage(element, 0);
	}

	
}
