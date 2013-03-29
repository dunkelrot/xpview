package com.basf.xpview.model.issues.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class IssueGroupAdapter implements IWorkbenchAdapter {

	@Override
	public Object[] getChildren(Object o) {
		IssueGroup issueGroup = (IssueGroup) o;
		return issueGroup.getIssues().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		IssueGroup issueGroup = (IssueGroup) o;
		return issueGroup.getName();
	}

	@Override
	public Object getParent(Object o) {
		IssueGroup issueGroup = (IssueGroup) o;
		return issueGroup.getIssueList();
	}

	
}
