package com.basf.xpview.model.events;

import com.basf.xpview.core.EventType;

/**
 * Events which this plug-in can send.
 * 
 * @author Arndt Teinert
 *
 */
public class EventTypes {

	static public final EventType FileImported 		  = new EventType("com.basf.xpview.model.FileImported");
	static public final EventType SceneGraphModified  = new EventType("com.basf.xpview.model.SceneGraphModified");
	static public final EventType IssueReported 	  = new EventType("com.basf.xpview.model.IssueReported");
	static public final EventType IssueListCreated 	  = new EventType("com.basf.xpview.model.IssueListCreated");
	static public final EventType IssueListRemoved 	  = new EventType("com.basf.xpview.model.IssueListRemoved");
	static public final EventType IssueListRemovedAll = new EventType("com.basf.xpview.model.IssueListRemovedAll");
}
