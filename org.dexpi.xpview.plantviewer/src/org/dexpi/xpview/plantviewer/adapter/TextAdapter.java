package org.dexpi.xpview.plantviewer.adapter;

import org.dexpi.xpview.model.Text;
import org.dexpi.xpview.plantviewer.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class TextAdapter implements IWorkbenchAdapter {

	protected ImageDescriptor icon;
	
	public TextAdapter() {
		this.icon = Activator.getImageDescriptor("icons/sticky-note-text.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		return new Object[0];
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		Text text = (Text) o;
		return text.getValue();
	}

	@Override
	public Object getParent(Object o) {
		Text text = (Text) o;
		return text.getTextList();
	}
	
}
