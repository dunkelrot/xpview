package com.basf.xpview.utils;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * Content provider for Tree controls. 
 * This provider uses the given AdapterFactory to access the {@link IWorkbenchAdapter} implementation.
 * The AdapterFactory approach allows to use the same implementation with different {@link IWorkbenchAdapter} 
 * implementations for the same object types.
 * 
 * @author Arndt Teinert
 *
 */
public class WorkspaceContentProvider implements ITreeContentProvider {

	protected AdapterFactory factory;

	public WorkspaceContentProvider(AdapterFactory factory) {
		this.factory = factory;
	}

	@Override
	public void dispose() {
		// ignored
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// ignored
	}

	@Override
	public Object[] getElements(Object parent) {
		Object[] result = null;
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) factory.getAdapter(parent, IWorkbenchAdapter.class);
		if (adapter != null) {
			result = adapter.getChildren(parent);
		}
		return result;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] result = null;
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) factory.getAdapter(parentElement, IWorkbenchAdapter.class);
		if (adapter != null) {
			result = adapter.getChildren(parentElement);
		}
		return result;
	}

	@Override
	public Object getParent(Object element) {
		Object result = null;
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) factory.getAdapter(element, IWorkbenchAdapter.class);
		if (adapter != null) {
			result = adapter.getParent(element);
		}
		return result;
	}

	@Override
	public boolean hasChildren(Object element) {
		boolean result = false;
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) factory.getAdapter(element, IWorkbenchAdapter.class);
		if (adapter != null) {
			result = adapter.getChildren(element).length != 0;
		}
		return result;
	}

}
