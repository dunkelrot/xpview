package org.dexpi.xpview.model.issues.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.issues.IssueTracker;

public class IssueTrackerAdapter implements IWorkbenchAdapter {

	@Override
	public Object[] getChildren(Object o) {
		IssueTracker issueTracker = (IssueTracker) o;
		return issueTracker.getIssueLists().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		return "Issues";
	}

	@Override
	public Object getParent(Object o) {
		return null;
	}

}
