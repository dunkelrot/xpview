package org.dexpi.xpview.plantviewer.adapter;

import org.dexpi.xpview.model.TextList;
import org.dexpi.xpview.plantviewer.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class TextListAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public TextListAdapter() {
		this.icon = Activator.getImageDescriptor("icons/sticky-notes-text.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		TextList textList = (TextList) o;
		return textList.getTexts().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		return "Texts";
	}

	@Override
	public Object getParent(Object o) {
		TextList textList = (TextList) o;
		return textList.getTextBag();
	}

}
