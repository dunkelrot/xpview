package org.dexpi.xpview.model.issues.adapter;

import org.dexpi.xpview.model.issues.Issue;

public class IssueWrapper {

	protected Issue issue;
	protected IssueGroup collection;
	
	public IssueWrapper(IssueGroup collection, Issue issue) {
		this.issue = issue;
		this.collection = collection;
	}
	
	public IssueGroup getCollection() {
		return collection;
	}
	
	public Issue getIssue() {
		return issue;
	}
	
}
