package org.dexpi.xpview.model.handlers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import org.dexpi.xpview.model.report.ExcelReport;
import org.dexpi.xpview.model.report.ReportPropertyData;
import org.dexpi.xpview.model.report.ReportWizard;

public class ExcelReportHandler extends AbstractHandler {

	public ExcelReportHandler() {

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ReportWizard wizard = new ReportWizard();
		WizardDialog dlg = new WizardDialog(PlatformUI.getWorkbench()
				.getDisplay().getActiveShell(), wizard);
		int res = dlg.open();
		if (res == Window.OK) {

			ReportPropertyData reportPropertyData = wizard.getReportPropertyData();
			ExcelReport report = new ExcelReport(reportPropertyData);
			try {
				report.report(new File(reportPropertyData.getOutputFilePath()));
				Desktop dt = Desktop.getDesktop();
			    dt.open(new File(reportPropertyData.getOutputFilePath()));

			} catch (FileNotFoundException ex) {
				MessageDialog.openError(PlatformUI.getWorkbench()
						.getDisplay().getActiveShell(), "Error", "Cannot access file " + reportPropertyData.getOutputFilePath());
			} catch (IOException ex) {
				MessageDialog.openError(PlatformUI.getWorkbench()
						.getDisplay().getActiveShell(), "Error", "Cannot write to file " + reportPropertyData.getOutputFilePath());
			}
		}

		return null;
	}
}
