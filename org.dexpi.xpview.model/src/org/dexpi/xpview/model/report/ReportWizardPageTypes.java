package org.dexpi.xpview.model.report;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public class ReportWizardPageTypes extends WizardPage implements ContentVerificationListener {

	protected TypesControl typesControl;
	protected ReportPropertyData reportPropertyData;
	
	public ReportWizardPageTypes(String pageName, ReportPropertyData reportPropertyData) {
		super(pageName, "Select type of elements to report", null);
		this.reportPropertyData = reportPropertyData;
	}
	
	@Override
	public void createControl(Composite parent) {
		typesControl = new TypesControl(parent, reportPropertyData);
		typesControl.setVerifyListener(this);
		setControl(typesControl);
		setPageComplete(false);
		setErrorMessage("Select output file");
	}

	@Override
	public void setStatus(String message, boolean isComplete) {
		setPageComplete(isComplete);
		setErrorMessage(message);
	}
	
	public void updateControl() {
		typesControl.update();
	}

	public TypesControl getTypesControl() {
		return typesControl;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		typesControl.dispose();
	}
}
