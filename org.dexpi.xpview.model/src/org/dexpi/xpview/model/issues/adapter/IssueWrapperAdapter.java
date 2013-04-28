package org.dexpi.xpview.model.issues.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.Activator;
import org.dexpi.xpview.model.issues.Issue;

public class IssueWrapperAdapter extends LabelProvider implements
		IWorkbenchAdapter, ITableLabelProvider {

	protected ImageDescriptor iconError;
	protected ImageDescriptor iconWarning;
	protected ImageDescriptor iconInfo;
	protected ImageDescriptor iconDefault;

	public IssueWrapperAdapter() {
		this.iconDefault = Activator.getImageDescriptor("icons/heart.png"); //$NON-NLS-1$
		this.iconError = Activator.getImageDescriptor("icons/exclamation.png"); //$NON-NLS-1$
		this.iconWarning = Activator.getImageDescriptor("icons/error.png"); //$NON-NLS-1$
		this.iconInfo = Activator.getImageDescriptor("icons/information.png"); //$NON-NLS-1$
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		IssueWrapper issueWrapper = (IssueWrapper) element;
		Issue issue = issueWrapper.getIssue();
		String text = "";

		if (columnIndex == 0) {
			text = issue.getMessage();
		} else if (columnIndex == 1) {
			text = issue.getSourceName();
		} else if (columnIndex == 2) {
			text = issue.getType().toString();
		}
		return text;
	}

	@Override
	public Object[] getChildren(Object o) {
		return new Object[0];
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		ImageDescriptor icon = iconDefault;
		IssueWrapper issueWrapper = (IssueWrapper) object;
		Issue issue = issueWrapper.getIssue();
		switch (issue.getType()) {
		case ERROR:
			icon = iconError;
			break;
		case WARNING:
			icon = iconWarning;
			break;
		case INFO:
			icon = iconInfo;
			break;
		}
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		IssueWrapper issueWrapper = (IssueWrapper) o;
		Issue issue = issueWrapper.getIssue();
		return issue.getMessage();
	}

	@Override
	public Object getParent(Object o) {
		IssueWrapper issueWrapper = (IssueWrapper) o;
		return issueWrapper.getCollection();
	}

}
