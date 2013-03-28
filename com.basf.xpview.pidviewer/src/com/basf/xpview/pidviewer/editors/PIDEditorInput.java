package com.basf.xpview.pidviewer.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.basf.xpview.model.graphics.SoNode;

/**
 * Input for the PIDEditorInput.
 * 
 * @author Arndt Teinert
 *
 */
public class PIDEditorInput implements IEditorInput {

	protected SoNode node;
	
	public PIDEditorInput(SoNode node) {
		this.node = node;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return node.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "";
	}

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (obj instanceof PIDEditorInput) {
        	return node.equals(((PIDEditorInput)obj).node);
        }
        return false;
    }	

    public int hashCode() {
        return node.hashCode();
    }

    public SoNode getNode() {
		return node;
	}
}
