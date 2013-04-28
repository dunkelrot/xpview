package org.dexpi.xpview.model.events;

import org.dexpi.xpview.core.EventType;

/**
 * Events which this plug-in can send.
 * 
 * @author Arndt Teinert
 *
 */
public class EventTypes {

	static public final EventType FileImported 		  = new EventType("org.dexpi.xpview.model.FileImported");
	static public final EventType SceneGraphModified  = new EventType("org.dexpi.xpview.model.SceneGraphModified");
	static public final EventType IssueReported 	  = new EventType("org.dexpi.xpview.model.IssueReported");
	static public final EventType IssueListCreated 	  = new EventType("org.dexpi.xpview.model.IssueListCreated");
	static public final EventType IssueListRemoved 	  = new EventType("org.dexpi.xpview.model.IssueListRemoved");
	static public final EventType IssueListRemovedAll = new EventType("org.dexpi.xpview.model.IssueListRemovedAll");
	static public final EventType TypeOrClassChanged  = new EventType("org.dexpi.xpview.model.TypeOrClassChanged");
}
