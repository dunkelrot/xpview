package com.basf.xpview.model.report;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

import com.basf.xpview.model.report.adapter.ReportPropertyAdapter;
import com.basf.xpview.model.report.adapter.ReportPropertyDataAdapter;
import com.basf.xpview.model.report.adapter.ReportPropertyListAdapter;
import com.basf.xpview.utils.AdapterFactory;
import com.basf.xpview.utils.WorkspaceContentProvider;
import com.basf.xpview.utils.WorkspaceLabelProvider;

public class PropertiesControl extends Composite implements ICheckStateListener {

	protected ReportPropertyData reportPropertyData;
	protected ContainerCheckedTreeViewer viewer;
	
	public PropertiesControl(Composite parent, ReportPropertyData reportPropertyData) {
		super(parent, SWT.NONE);
		this.reportPropertyData = reportPropertyData;
		init(parent);
	}

	public void init(Composite parent) {

		GridLayout gdl = new GridLayout(1, false);
		setLayout(gdl);

		GridData gd04 = new GridData(SWT.FILL, SWT.FILL, true, true);

		AdapterFactory factory = new AdapterFactory();
		factory.registerAdapter(new ReportPropertyDataAdapter(), ReportPropertyData.class);
		factory.registerAdapter(new ReportPropertyAdapter(), ReportProperty.class);
		factory.registerAdapter(new ReportPropertyListAdapter(), ReportPropertyList.class);
		
		viewer = new ContainerCheckedTreeViewer(this, SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new WorkspaceContentProvider(factory));
		viewer.setLabelProvider(new WorkspaceLabelProvider(factory));

		viewer.getTree().setLayoutData(gd04);
		viewer.setInput(reportPropertyData);
		
		viewer.addCheckStateListener(this);
	}
	
	public void updateView() {
		viewer.refresh();
	}
	
	protected void checkPage() {
	}
	
	@Override
	public void checkStateChanged(CheckStateChangedEvent event) {
		if (event.getElement() instanceof ReportProperty) {
			((ReportProperty) event.getElement()).setChecked(event.getChecked());
		}
	}
	
}
