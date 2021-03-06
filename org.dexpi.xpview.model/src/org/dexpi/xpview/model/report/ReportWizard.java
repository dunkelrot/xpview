package org.dexpi.xpview.model.report;

import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;

public class ReportWizard extends Wizard {

	protected ReportPropertyData reportPropertyData;
	protected ReportWizardPageProperties propertiesPage;
	protected ReportWizardPageTypes typesPage;

	public ReportWizard() {
		this.reportPropertyData = new ReportPropertyData();
	}

	@Override
	public void addPages() {

		super.addPages();

		IWizardContainer wizardContainer = this.getContainer();
		if (wizardContainer instanceof IPageChangeProvider) {
			IPageChangeProvider pageChangeProvider = (IPageChangeProvider) wizardContainer;
			pageChangeProvider.addPageChangedListener(new IPageChangedListener() {
				@Override
				public void pageChanged(PageChangedEvent event) {
					if (event.getSelectedPage() == propertiesPage) {
						propertiesPage.updateControl();
					}
					if (event.getSelectedPage() == typesPage) {
						typesPage.updateControl();
					}
				}
			});
		}

		typesPage = new ReportWizardPageTypes("Types", reportPropertyData);
		propertiesPage = new ReportWizardPageProperties("Properties", reportPropertyData);
		
		addPage(typesPage);
		addPage(propertiesPage);
	}

	@Override
	public boolean performFinish() {
		return true;
	}

	public ReportPropertyData getReportPropertyData() {
		return reportPropertyData;
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
