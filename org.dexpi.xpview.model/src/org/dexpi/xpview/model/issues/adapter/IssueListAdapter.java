package org.dexpi.xpview.model.issues.adapter;

import java.util.ArrayList;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.issues.Issue;
import org.dexpi.xpview.model.issues.IssueList;
import org.dexpi.xpview.model.issues.IssueTracker;

public class IssueListAdapter implements IWorkbenchAdapter {

	@Override
	public Object[] getChildren(Object o) {
		IssueList issueList = (IssueList) o;
		
		IssueGroup errors = new IssueGroup("Errors", issueList);
		IssueGroup warning = new IssueGroup("Warnings", issueList);
		IssueGroup info = new IssueGroup("Infos", issueList);
		
		for (Issue issue : issueList.getIssues()) {
			switch (issue.getType()) {
			case ERROR:
				errors.add(issue);
				break;
			case WARNING:
				warning.add(issue);
				break;
			case INFO:
				info.add(issue);
				break;
			}
		}
		
		ArrayList<IssueGroup> collections = new ArrayList<IssueGroup>();
		if (errors.issues.size() > 0) {
			collections.add(errors);
		}
		if (warning.issues.size() > 0) {
			collections.add(warning);
		}
		if (info.issues.size() > 0) {
			collections.add(info);
		}
		return collections.toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		IssueList issueList = (IssueList) o;
		return "Plant model: " + issueList.getPlant().getName();
	}

	@Override
	public Object getParent(Object o) {
		return IssueTracker.getInstance();
	}

}
