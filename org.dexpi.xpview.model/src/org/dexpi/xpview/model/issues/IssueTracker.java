package org.dexpi.xpview.model.issues;

import java.util.ArrayList;
import java.util.List;

import org.dexpi.xpview.core.Event;
import org.dexpi.xpview.core.EventManager;
import org.dexpi.xpview.model.Plant;
import org.dexpi.xpview.model.events.EventTypes;

public class IssueTracker {

	protected ArrayList<IssueList> issueLists;
	
	private static IssueTracker instance;
	
	public static IssueTracker getInstance() {
		if (instance == null) {
			instance = new IssueTracker();
		}
		return instance;
	}
	
	private IssueTracker() {
		this.issueLists = new ArrayList<IssueList>();
	}
	
	public List<IssueList> getIssueLists() {
		return issueLists;
	}
	
	public IssueList addIssueList(Plant plant) {
		IssueList issueList = new IssueList(plant);
		issueLists.add(issueList);
		EventManager.getInstance().sendEvent(new Event(this, EventTypes.IssueListCreated, issueList));
		return issueList;
	}
	
	public void removeIssueList(Plant plant) {
		IssueList toRemove = null;
		for (IssueList il : issueLists) {
			if (il.getPlant() == plant) {
				toRemove = il;
				break;
			}
		}
		if (toRemove != null) {
			issueLists.remove(toRemove);
			EventManager.getInstance().sendEvent(new Event(this, EventTypes.IssueListRemoved, toRemove));
		}
	}
	
	public void clear() {
		issueLists.clear();
		EventManager.getInstance().sendEvent(new Event(this, EventTypes.IssueListRemovedAll, issueLists));
	}
}

