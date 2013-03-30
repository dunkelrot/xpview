package com.basf.xpview.model.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import com.basf.xpview.model.Equipment;
import com.basf.xpview.model.Workspace;
import com.basf.xpview.model.report.ReportPropertyData;
import com.basf.xpview.model.report.ReportService;
import com.basf.xpview.model.report.ReportWizard;

public class ExcelReport extends AbstractHandler {

	public ExcelReport() {

	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		ReportWizard wizard = new ReportWizard();
		WizardDialog dlg = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
		int res = dlg.open();
		if (res == Window.OK) {
		}
		
		return null;
	}
}
