package org.dexpi.xpview.model.report;

import org.dexpi.xpview.model.Property;

public class ReportProperty {

	protected boolean checked;
	protected boolean duplicated;
	
	protected ReportPropertyList parent;
	protected Property property;
	
	public ReportProperty(ReportPropertyList parent, Property property) {
		this.parent = parent;
		this.property = property;
	}
	
	public ReportPropertyList getParent() {
		return parent;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public Property getProperty() {
		return property;
	}
	
	public boolean isDuplicated() {
		return duplicated;
	}
	
	public void setDuplicated(boolean duplicated) {
		this.duplicated = duplicated;
	}
}
