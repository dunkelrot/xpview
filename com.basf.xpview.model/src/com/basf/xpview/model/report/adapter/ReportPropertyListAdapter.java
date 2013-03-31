package com.basf.xpview.model.report.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.basf.xpview.model.Activator;
import com.basf.xpview.model.report.ReportPropertyList;

public class ReportPropertyListAdapter implements IWorkbenchAdapter, ICheckStateProvider {

	protected ImageDescriptor icon;
	
	public ReportPropertyListAdapter() {
		this.icon = Activator.getImageDescriptor("icons/property.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		ReportPropertyList reportPropertyList = (ReportPropertyList) o;
		return reportPropertyList.getPropertyList().toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return icon;
	}

	@Override
	public String getLabel(Object o) {
		ReportPropertyList reportPropertyList = (ReportPropertyList) o;
		return reportPropertyList.getName();
	}

	@Override
	public Object getParent(Object o) {
		ReportPropertyList reportPropertyList = (ReportPropertyList) o;
		return reportPropertyList.getParent();
	}

	@Override
	public boolean isChecked(Object element) {
		ReportPropertyList reportPropertyList = (ReportPropertyList) element;
		return reportPropertyList.isOnePropertyChecked();
	}

	@Override
	public boolean isGrayed(Object element) {
		ReportPropertyList reportPropertyList = (ReportPropertyList) element;
		return reportPropertyList.isOnePropertyUnchecked();
	}

}
