package org.dexpi.xpview.utils;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog show the exception message and the stacktrace.
 * The user can export the provided information into a file.
 * 
 * @author Arndt Teinert
 *
 */
public class ExceptionDialog extends Dialog {

	ExceptionDialogCtrl control;
	Throwable throwable;
	
	public ExceptionDialog(Shell parentShell, Throwable throwable) {
		super(parentShell);
		this.throwable = throwable;
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
	protected Control createButtonBar(Composite parent) {
		Control ctrl = super.createButtonBar(parent);
		return ctrl;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		control =  new ExceptionDialogCtrl(parent, SWT.NONE);
		control.setThrowable(throwable);
		
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
		newShell.setText("Error");
	}

	/**
	 * Call this method to open the dialog with the given exception.
	 * 
	 * @param exception the exception to show
	 */
	public static void openException(Exception exception) {
		ExceptionDialog dlg = new ExceptionDialog(Display.getCurrent().getActiveShell(), exception);
		dlg.setBlockOnOpen(true);
		dlg.open();
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
}
