package com.basf.xpview.plantviewer.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.basf.xpview.model.Property;

/**
 * Input for the URIEditor. A thin wrapper around a Property which
 * provides access to the URI.
 * 
 * @author Arndt Teinert
 *
 */
public class URIEditorInput implements IEditorInput {

	protected Property property;
	
	public URIEditorInput(Property property) {
		this.property = property;
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
		return property.getURI();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return property.getName() + " : " + property.getURI();
	}

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (obj instanceof URIEditorInput) {
        	return property.equals(((URIEditorInput)obj).property);
        }
        return false;
    }	

    public int hashCode() {
        return property.hashCode();
    }

    public String getURI() {
		return property.getURI();
	}
}
