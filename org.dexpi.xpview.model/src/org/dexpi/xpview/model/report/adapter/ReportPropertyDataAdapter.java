package org.dexpi.xpview.model.report.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.dexpi.xpview.model.report.ReportPropertyData;

public class ReportPropertyDataAdapter implements IWorkbenchAdapter {

	@Override
	public Object[] getChildren(Object o) {
		ReportPropertyData reportPropertyData = (ReportPropertyData) o;
		return reportPropertyData.getReportPropertyLists().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		return "Properties";
	}

	@Override
	public Object getParent(Object o) {
		return null;
	}
	
}
