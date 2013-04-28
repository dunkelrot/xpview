package org.dexpi.xpview.model.report;

public class ReportConfig {
	
	protected String name;
	
	public class ReportConfigEntry {
		public String listName;
		public String propertyName;
	}
	
	public ReportConfig(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
}
