package com.basf.xpview.pidviewer.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

/**
 * URI 'Editor' which opens the given URI (URIEditorInput) in a browser.
 * 
 * @author Arndt Teinert
 *
 */
public class PIDEditor extends EditorPart {

	public static final String ID = "com.basf.xpview.pidviewer.editors.PIDEditor";
	
	protected PIDEditorViewer viewer;
	protected PIDEditorInput input;
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		
	}

	@Override
	public void doSaveAs() {
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		
		setSite(site);
		if (input instanceof PIDEditorInput) {
			this.input = (PIDEditorInput) input;
			setInput(input);
			firePropertyChange(PROP_TITLE);
			setPartName(input.getName());
		}
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new PIDEditorViewer(parent);
		viewer.init();
		viewer.setInput(((PIDEditorInput) input).getNode());
	}

	@Override
	public void setFocus() {
		viewer.setFocus();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (viewer != null) {
			viewer.dispose();
		}
	}

}