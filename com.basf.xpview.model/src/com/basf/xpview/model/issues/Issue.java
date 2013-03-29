package com.basf.xpview.model.issues;

public class Issue {

	protected String message;
	protected Object source;
	protected IssueType type;
	protected IssueList issueList;
	protected String sourceName;
	
	public Issue(IssueList issueList, String message, Object source, IssueType type) {
		this.message = message;
		this.source = source;
		this.sourceName = source.toString();
		this.type = type;
		this.issueList = issueList;
	}
	
	public Issue(IssueList issueList, String message, String sourceName, IssueType type) {
		this.message = message;
		this.sourceName = sourceName;
		this.type = type;
		this.issueList = issueList;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Object getSource() {
		return source;
	}
	
	public IssueType getType() {
		return type;
	}
	
	public IssueList getIssueList() {
		return issueList;
	}
	
	public String getSourceName() {
		return sourceName;
	}
}
