package org.dexpi.xpview.model.report;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

import org.dexpi.xpview.core.Event;
import org.dexpi.xpview.core.EventListener;
import org.dexpi.xpview.core.EventManager;
import org.dexpi.xpview.model.events.EventTypes;
import org.dexpi.xpview.model.report.adapter.ReportPropertyAdapter;
import org.dexpi.xpview.model.report.adapter.ReportPropertyDataAdapter;
import org.dexpi.xpview.model.report.adapter.ReportPropertyListAdapter;
import org.dexpi.xpview.model.report.config.Configuration;
import org.dexpi.xpview.utils.AdapterFactory;
import org.dexpi.xpview.utils.WorkspaceContentProvider;
import org.dexpi.xpview.utils.WorkspaceLabelProvider;

public class PropertiesControl extends Composite implements ICheckStateListener, EventListener {

	protected Label label01;
	protected ReportPropertyData reportPropertyData;
	protected ContainerCheckedTreeViewer viewer;
	protected ReportPropertyAdapter reportPropertyAdapter;
	protected ReportPropertyListAdapter reportPropertyListAdapter;
	protected Combo comboConfig;

	public PropertiesControl(Composite parent, ReportPropertyData reportPropertyData) {
		super(parent, SWT.NONE);
		this.reportPropertyData = reportPropertyData;
		this.reportPropertyAdapter = new ReportPropertyAdapter();
		this.reportPropertyListAdapter = new ReportPropertyListAdapter();
		init(parent);
	}

	public void init(Composite parent) {

		GridLayout gdl = new GridLayout(2, false);
		setLayout(gdl);

		Label label04 = new Label(this, SWT.NONE);
		label04.setText("Configuration:");

		comboConfig = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);

		GridData gd05 = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd05.horizontalSpan = 2;
		comboConfig.setLayoutData(gd05);
		comboConfig.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				reportPropertyData.setConfigurationName(comboConfig.getText());
				reportPropertyData.setCheckedAll(false);
				ReportConfigurations.getInstance().load(reportPropertyData.getConfigurationName(), reportPropertyData);
				viewer.refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		for (Configuration config : ReportConfigurations.getInstance().getConfigurationList().getConfiguration()) {
			comboConfig.add(config.getName());
		}
		
		GridData gd04 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd04.horizontalSpan = 2;

		AdapterFactory factory = new AdapterFactory();
		factory.registerAdapter(new ReportPropertyDataAdapter(), ReportPropertyData.class);
		factory.registerAdapter(reportPropertyAdapter, ReportProperty.class);
		factory.registerAdapter(reportPropertyListAdapter, ReportPropertyList.class);

		viewer = new ContainerCheckedTreeViewer(this, SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new WorkspaceContentProvider(factory));
		viewer.setLabelProvider(new WorkspaceLabelProvider(factory));
		viewer.setCheckStateProvider(new ICheckStateProvider() {

			@Override
			public boolean isChecked(Object element) {
				if (element instanceof ReportPropertyList) {
					return reportPropertyListAdapter.isChecked(element);
				}
				if (element instanceof ReportProperty) {
					return reportPropertyAdapter.isChecked(element);
				}
				return false;
			}

			@Override
			public boolean isGrayed(Object element) {
				if (element instanceof ReportPropertyList) {
					return reportPropertyListAdapter.isGrayed(element);
				}
				if (element instanceof ReportProperty) {
					return reportPropertyAdapter.isGrayed(element);
				}
				return false;
			}

		});

		viewer.getTree().setLayoutData(gd04);
		viewer.setInput(reportPropertyData);

		viewer.addCheckStateListener(this);

		GridData gd02 = new GridData(SWT.FILL, SWT.FILL, false, false);
		Button saveConfig = new Button(this, SWT.PUSH);
		saveConfig.setLayoutData(gd02);
		saveConfig.setText("Save configuration");
		saveConfig.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ReportConfigNameDialog dlg = new ReportConfigNameDialog(PropertiesControl.this.getShell());
				if (dlg.open() == Window.OK) {
					ReportConfigurations.getInstance().save(dlg.getConfigurationName(), reportPropertyData);
					label01.setText("Saved as " + dlg.getConfigurationName());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		GridData gd03 = new GridData(SWT.FILL, SWT.CENTER, true, false);
		label01 = new Label(this, SWT.NONE);
		label01.setLayoutData(gd03);
		
		EventManager.getInstance().registerForEvent(EventTypes.TypeOrClassChanged, this);
	}

	public void update() {
		label01.setText("");
		viewer.refresh();
	}

	@Override
	public void checkStateChanged(CheckStateChangedEvent event) {
		if (event.getElement() instanceof ReportProperty) {
			((ReportProperty) event.getElement()).setChecked(event.getChecked());
		}
		if (event.getElement() instanceof ReportPropertyList) {
			((ReportPropertyList) event.getElement()).setChecked(event.getChecked());
		}
		label01.setText("");
		comboConfig.deselectAll();
	}

	protected void updateConfig() {

		String selected = comboConfig.getText();
		comboConfig.removeAll();
		for (Configuration config : ReportConfigurations.getInstance().getConfigurationList().getConfiguration()) {
			comboConfig.add(config.getName());
		}
		comboConfig.setText(selected);
		reportPropertyData.setConfigurationName(comboConfig.getText());

	}

	
	@Override
	public void dispose() {
		EventManager.getInstance().unregisterForEvent(EventTypes.TypeOrClassChanged, this);
	}

	@Override
	public void onEvent(Event event) {
		if (event.getType().is(EventTypes.TypeOrClassChanged)) {
			comboConfig.deselectAll();
		}
	}
	
}
