package com.basf.xpview.plantviewer.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

/**
 * A thin wrapper around the browser control.
 * 
 * @author Arndt Teinert
 *
 */
public class URIEditorViewer {

	protected Browser browser; 
	protected Composite parent;
	
	public URIEditorViewer(Composite parent) {
		this.parent = parent;		
	}
	
	public void init() {
		browser = new Browser(parent, SWT.NONE);
	}
	
	public void setFocus() {
		parent.setFocus();
	}
	
	public void setInput(String uri) {
		browser.setUrl(uri);
	}
	
}
