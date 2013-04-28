package org.dexpi.xpview.model.report;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


public class ReportConfigNameDialog extends Dialog {

	private ReportConfigNameControl control;
	protected String configurationName;
	
	public ReportConfigNameDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Control ctrl = super.createContents(parent);
		
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;
		
		ctrl.setLayoutData(gd);		
		
		return ctrl;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		control =  new ReportConfigNameControl(parent);
		
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;
		
		control.setLayoutData(gd);
		return control;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Enter configuration name");
	}
	
	@Override
	protected void okPressed() {
		configurationName = control.getConfigName();
		super.okPressed();
	}
	
	public String getConfigurationName() {
		return configurationName;
	}
}
