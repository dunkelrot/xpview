package com.basf.xpview.model.report;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public class ReportWizardPageProperties extends WizardPage {

	protected PropertiesControl control;
	protected ReportPropertyData reportPropertyData;
	
	public ReportWizardPageProperties(String pageName, ReportPropertyData reportPropertyData) {
		super(pageName, "Select properties", null);
		this.reportPropertyData = reportPropertyData;
	}
	
	@Override
	public void createControl(Composite parent) {

		this.control = new PropertiesControl(parent, reportPropertyData);
		setControl(control);
		setPageComplete(true);
	}
	
	public void updateControl() {
		control.update();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		control.dispose();
	}
}
