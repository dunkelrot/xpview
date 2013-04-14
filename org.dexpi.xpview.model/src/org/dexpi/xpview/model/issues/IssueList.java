package com.basf.xpview.model.issues;

import java.util.ArrayList;
import java.util.List;

import com.basf.xpview.core.Event;
import com.basf.xpview.core.EventManager;
import com.basf.xpview.model.Plant;
import com.basf.xpview.model.events.EventTypes;

public class IssueList {

	protected Plant plant;
	protected ArrayList<Issue> issues;
	
	public IssueList(Plant plant) {
		this.plant = plant;
		this.issues = new ArrayList<Issue>();
	}
	
	public Plant getPlant() {
		return plant;
	}
	
	public Issue addIssue(String message, Object source, IssueType type) {
		Issue issue = new Issue(this, message, source, type);
		issues.add(issue);
		EventManager.getInstance().sendEvent(new Event(this, EventTypes.IssueReported, issue));
		return issue;
	}
	
	public Issue addIssue(String message, String source, IssueType type) {
		Issue issue = new Issue(this, message, source, type);
		issues.add(issue);
		EventManager.getInstance().sendEvent(new Event(this, EventTypes.IssueReported, issue));
		return issue;
	}
	
	public List<Issue> getIssues() {
		return issues;
	}
}
