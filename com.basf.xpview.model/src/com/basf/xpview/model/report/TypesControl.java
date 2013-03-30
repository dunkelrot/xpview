package com.basf.xpview.model.report;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.basf.xpview.model.Equipment;
import com.basf.xpview.model.Nozzle;
import com.basf.xpview.model.PlantItem;
import com.basf.xpview.model.Workspace;

public class TypesControl extends Composite {

	protected ContentVerificationListener verifyListener;
	
	protected Combo comboTypes;
	protected Combo comboClassNames;

	public TypesControl(Composite parent) {
		super(parent, SWT.NONE);
		init(parent);
	}

	public void setVerifyListener(ContentVerificationListener verifyListener) {
		this.verifyListener = verifyListener;
	}
	
	public void init(Composite parent) {

		GridLayout gdl = new GridLayout(2, false);
		setLayout(gdl);

		Label label03 = new Label(this, SWT.NONE);
		label03.setText("Types:");

		comboTypes = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboTypes.add(Equipment.class.getSimpleName());
		comboTypes.add(Nozzle.class.getSimpleName());

		GridData gd03 = new GridData(SWT.FILL, SWT.FILL, true, false);
		comboTypes.setLayoutData(gd03);
		comboTypes.select(0);
		comboTypes.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateClassNames();
				if (verifyListener != null) {
					verifyListener.setStatus(null, false);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		Label label02 = new Label(this, SWT.NONE);
		label02.setText("Classes:");

		comboClassNames = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);

		GridData gd04 = new GridData(SWT.FILL, SWT.FILL, true, false);
		comboClassNames.setLayoutData(gd04);
		comboClassNames.select(0);
		
		comboClassNames.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (verifyListener != null) {
					verifyListener.setStatus(null, true);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		updateClassNames();
	}

	protected void updateClassNames() {
		String typeName = comboTypes.getText();
		Set<String> classNames = null;
		if (typeName.equals(Equipment.class.getSimpleName())) {
			classNames = ReportService.getAllComponentClasses(Workspace.getInstance().getPlant(), Equipment.class);
		} else if (typeName.equals(Nozzle.class.getSimpleName())) {
			classNames = ReportService.getAllComponentClasses(Workspace.getInstance().getPlant(), Nozzle.class);
		}
		if (classNames != null) {
			comboClassNames.removeAll();
			for (String className : classNames) {
				comboClassNames.add(className);
			}
		}
	}

	protected void checkPage() {
		
	}
	
	public String getClassName() {
		return comboClassNames.getText();
	}
	
	public Class<?> getType() {
		String typeName = comboTypes.getText();
		if (typeName.equals(Equipment.class.getSimpleName())) {
			return Equipment.class;
		} else if (typeName.equals(Nozzle.class.getSimpleName())) {
			return Nozzle.class;
		}
		return PlantItem.class;
	}
}

