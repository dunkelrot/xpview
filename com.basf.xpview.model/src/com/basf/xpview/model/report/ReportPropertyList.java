package com.basf.xpview.model.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.basf.xpview.model.Property;

public class ReportPropertyList implements Iterable<ReportProperty> {
	
	protected ArrayList<ReportProperty> propertyList;
	protected ReportPropertyData parent;
	protected String name;
	
	public ReportPropertyList(ReportPropertyData parent, String name) {
		this.propertyList = new ArrayList<ReportProperty>();
		this.parent = parent;
		this.name = name;
	}

	public List<ReportProperty> getPropertyList() {
		return propertyList;
	}

	public String getName() {
		return name;
	}
	
	public ReportPropertyData getParent() {
		return parent;
	}
	
	public ReportProperty getByName(String name) {
		ReportProperty result = null;
		for (ReportProperty reportProp : propertyList) {
			if (reportProp.getProperty().getName().equals(name)) {
				result = reportProp;
				break;
			}
		}
		return result;
	}
	
	public ReportProperty addProperty(Property property) {
		ReportProperty reportProperty = getByName(property.getName());
		if (reportProperty != null) {
			reportProperty.setDuplicated(true);
		} else {
			reportProperty = new ReportProperty(this, property);
			propertyList.add(reportProperty);
		}
		return reportProperty;
	}
	
	@Override
	public Iterator<ReportProperty> iterator() {
		return propertyList.iterator();
	}
	
	public void setChecked(boolean checked) {
		for (ReportProperty reportProp : propertyList) {
			reportProp.setChecked(checked);
		}
	}

	public boolean isOnePropertyChecked() {
		for (ReportProperty reportProp : propertyList) {
			if (reportProp.isChecked() == true) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isOnePropertyUnchecked() {
		for (ReportProperty reportProp : propertyList) {
			if (reportProp.isChecked() == false) {
				return true;
			}
		}
		return false;
	}
}
