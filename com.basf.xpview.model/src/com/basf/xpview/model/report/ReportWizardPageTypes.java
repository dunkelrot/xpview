package com.basf.xpview.model.report;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public class ReportWizardPageTypes extends WizardPage implements ContentVerificationListener {

	protected TypesControl control;
	protected ReportPropertyData reportPropertyData;
	
	public ReportWizardPageTypes(String pageName, ReportPropertyData reportPropertyData) {
		super(pageName, "Select type of elements to report", null);
	}
	
	@Override
	public void createControl(Composite parent) {
		control = new TypesControl(parent);
		control.setVerifyListener(this);
		setControl(control);
		setPageComplete(false);
	}

	@Override
	public void setStatus(String message, boolean isComplete) {
		setPageComplete(isComplete);
		setErrorMessage(message);
	}
	
	public Class<?> getType() {
		return control.getType();
	}
	
	public String getClassName() {
		return control.getClassName();
	}
	
}
