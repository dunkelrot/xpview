package com.basf.xpview.model.report.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.basf.xpview.model.Activator;
import com.basf.xpview.model.report.ReportProperty;

public class ReportPropertyAdapter implements IWorkbenchAdapter, ICheckStateProvider {

	protected ImageDescriptor iconDuplicate;
	protected ImageDescriptor iconSingle;
	
	public ReportPropertyAdapter() {
		this.iconDuplicate = Activator.getImageDescriptor("icons/status-busy.png"); //$NON-NLS-1$
		this.iconSingle = Activator.getImageDescriptor("icons/status.png"); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren(Object o) {
		return new Object[0];
	}
	
	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		ReportProperty reportProperty = (ReportProperty) object;
		if (reportProperty.isDuplicated()) {
			return iconDuplicate;
		} else {
			return iconSingle;
		}
	}
	
	@Override
	public String getLabel(Object o) {
		ReportProperty reportProperty = (ReportProperty) o;
		return reportProperty.getProperty().getName();
	}
	
	@Override
	public Object getParent(Object o) {
		ReportProperty reportProperty = (ReportProperty) o;
		return reportProperty.getParent();
	}

	@Override
	public boolean isChecked(Object element) {
		ReportProperty reportProperty = (ReportProperty) element;
		return reportProperty.isChecked();
	}

	@Override
	public boolean isGrayed(Object element) {
		return false;
	}
	
}
