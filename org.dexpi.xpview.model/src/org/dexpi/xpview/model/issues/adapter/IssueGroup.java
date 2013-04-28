package org.dexpi.xpview.model.issues.adapter;

import java.util.ArrayList;
import java.util.List;

import org.dexpi.xpview.model.issues.Issue;
import org.dexpi.xpview.model.issues.IssueList;

public class IssueGroup {

	protected ArrayList<IssueWrapper> issues;
	protected IssueList issueList;
	protected String name;
	
	public IssueGroup(String name, IssueList issueList) {
		this.name = name;
		this.issueList = issueList;
		this.issues = new ArrayList<IssueWrapper>();
	}
	
	public List<IssueWrapper> getIssues() {
		return issues;
	}
	
	public IssueList getIssueList() {
		return issueList;
	}

	public String getName() {
		return name;
	}
	
	public void add(Issue issue) {
		IssueWrapper wrapper = new IssueWrapper(this, issue);
		issues.add(wrapper);
	}
}
