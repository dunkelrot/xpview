package com.basf.xpview.model.report;

import java.util.ArrayList;
import java.util.List;

public class ReportPropertyData {

	protected Class<?> plantItemType;
	protected String className;
	protected ArrayList<ReportPropertyList> reportPropertyLists;
	
	public ReportPropertyData() {
		this.reportPropertyLists = new ArrayList<ReportPropertyList>();
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public void setPlantItemType(Class<?> plantItemType) {
		this.plantItemType = plantItemType;
	}
	
	public ReportPropertyList get(String name) {
		ReportPropertyList result = null;
		for (ReportPropertyList repPropList : reportPropertyLists) {
			if (repPropList.getName().equals(name)) {
				result = repPropList;
				break;
			}
		}
		return result;
	}
	
	public ReportPropertyList add(String name) {
		
		ReportPropertyList result = get(name);
		if (result == null) {
			result = new ReportPropertyList(this, name);
			reportPropertyLists.add(result);
		}
		return result;
	}
	
	public List<ReportPropertyList> getReportPropertyLists() {
		return reportPropertyLists;
	}
	
	public String getClassName() {
		return className;
	}
	
	public Class<?> getPlantItemType() {
		return plantItemType;
	}
	
	public void clear() {
		reportPropertyLists.clear();
	}


	
}
