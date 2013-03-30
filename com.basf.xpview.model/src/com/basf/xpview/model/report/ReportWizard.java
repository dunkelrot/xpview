package com.basf.xpview.model.report;

import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;

import com.basf.xpview.model.Workspace;

public class ReportWizard extends Wizard {

	protected ReportConfiguration configuration;
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
			pageChangeProvider
					.addPageChangedListener(new IPageChangedListener() {
						@Override
						public void pageChanged(PageChangedEvent event) {
							if (event.getSelectedPage() == propertiesPage) {
								ReportService.updateReportPropertyData(
										reportPropertyData, Workspace
												.getInstance().getPlant(),
										typesPage.getType(), typesPage
												.getClassName());
								propertiesPage.updateControl();
							}
						}
					});
		}

		typesPage = new ReportWizardPageTypes("Types", reportPropertyData);
		addPage(typesPage);

		propertiesPage = new ReportWizardPageProperties("Properties",
				reportPropertyData);
		addPage(propertiesPage);
	}

	@Override
	public boolean performFinish() {
		return false;
	}

	public ReportConfiguration getConfiguration() {
		return configuration;
	}

}
