package org.dexpi.xpview.model.report;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ReportConfigNameControl extends Composite {

	private Text text01;
	
	public ReportConfigNameControl(Composite parent) {
		super(parent, SWT.NONE);
		init();
	}	
	
	private void init() {
		setLayout(new GridLayout(2, false));
		
		// GridData gd01 = new GridData(GridData.FILL_HORIZONTAL);
		Label label01 = new Label(this, SWT.NONE);
		label01.setText("Name: ");
		
		GridData gd02 = new GridData(GridData.FILL_HORIZONTAL);
		gd02.widthHint = 200;
		
		text01 = new Text(this, SWT.BORDER);
		text01.setText("");
		text01.setLayoutData(gd02);
		
		layout();
	}
	
	public String getConfigName() {
		return text01.getText();
	}
}
