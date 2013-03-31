package com.basf.xpview.model.report;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.basf.xpview.model.Equipment;
import com.basf.xpview.model.Nozzle;
import com.basf.xpview.model.PlantItem;
import com.basf.xpview.model.Workspace;
import com.basf.xpview.model.report.config.Configuration;

public class TypesControl extends Composite {

	protected ContentVerificationListener verifyListener;
	
	protected Combo comboTypes;
	protected Combo comboClassNames;
	protected Combo comboConfig;
	
	protected Text outputFile;
	protected Button selectOutputFile;
	
	public TypesControl(Composite parent) {
		super(parent, SWT.NONE);
		init(parent);
	}

	public void setVerifyListener(ContentVerificationListener verifyListener) {
		this.verifyListener = verifyListener;
	}
	
	public void init(Composite parent) {

		GridLayout gdl = new GridLayout(3, false);
		setLayout(gdl);

		Label label01 = new Label(this, SWT.NONE);
		label01.setText("Ouput file:");
		
		GridData gd01 = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd01.widthHint = 200;
		outputFile = new Text(this, SWT.BORDER);
		outputFile.setLayoutData(gd01);
		outputFile.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				if (outputFile.getText().trim().isEmpty()) {
					verifyListener.setStatus("Select output file", false);
				} else {
					verifyListener.setStatus(null, true);
				}
			}
		});
		
		GridData gd02 = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd02.widthHint = 30;
		selectOutputFile = new Button(this, SWT.PUSH);
		selectOutputFile.setLayoutData(gd02);
		selectOutputFile.setText("...");
		selectOutputFile.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fOpen = new FileDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell());
				fOpen.setFilterExtensions(new String[] { "*.xlsx" }); //$NON-NLS-1$
				fOpen.setFilterNames(new String[] { "Excel" });

				String fileSelected = fOpen.open();
				if (fileSelected != null) {
					outputFile.setText(fileSelected);
					verifyListener.setStatus(null, true);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		Label label03 = new Label(this, SWT.NONE);
		label03.setText("Types:");

		comboTypes = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboTypes.add(PlantItem.class.getSimpleName());
		comboTypes.add(Equipment.class.getSimpleName());
		comboTypes.add(Nozzle.class.getSimpleName());

		GridData gd03 = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd03.horizontalSpan = 2;
		comboTypes.setLayoutData(gd03);
		comboTypes.select(0);
		comboTypes.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateClassNames();
				if (verifyListener != null) {
					// verifyListener.setStatus(null, false);
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
		gd04.horizontalSpan = 2;
		comboClassNames.setLayoutData(gd04);
		comboClassNames.select(0);
		
		comboClassNames.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (verifyListener != null) {
					// verifyListener.setStatus(null, true);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		Label label04 = new Label(this, SWT.NONE);
		label04.setText("Configuration:");

		comboConfig = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);

		GridData gd05 = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd05.horizontalSpan = 2;
		comboConfig.setLayoutData(gd04);
		comboConfig.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		for (Configuration config : ReportConfigurations.getInstance().getConfigurationList().getConfiguration()) {
			comboConfig.add(config.getName());
		}

		updateClassNames();
	}

	protected void updateClassNames() {
		String typeName = comboTypes.getText();
		Set<String> classNames = null;
		if (typeName.equals(Equipment.class.getSimpleName())) {
			classNames = ReportService.getAllComponentClasses(Workspace.getInstance().getPlant(), Equipment.class);
		} else if (typeName.equals(Nozzle.class.getSimpleName())) {
			classNames = ReportService.getAllComponentClasses(Workspace.getInstance().getPlant(), Nozzle.class);
		} else if (typeName.equals(PlantItem.class.getSimpleName())) {
			classNames = ReportService.getAllComponentClasses(Workspace.getInstance().getPlant(), PlantItem.class);
		}
		if (classNames != null) {
			comboClassNames.removeAll();
			comboClassNames.add(ReportService.ClassNameALL);
			for (String className : classNames) {
				comboClassNames.add(className);
			}
			comboClassNames.select(0);
		}
	}

	public String getConfigurationName() {
		return comboConfig.getText();
	}
	
	protected void checkPage() {
		
	}
	
	public String getClassName() {
		return comboClassNames.getText();
	}
	
	public String getOutputFilePath() {
		return outputFile.getText();
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
	
	public void update() {
		String selected = comboConfig.getText();
		comboConfig.removeAll();
		for (Configuration config : ReportConfigurations.getInstance().getConfigurationList().getConfiguration()) {
			comboConfig.add(config.getName());
		}
		comboConfig.setText(selected);
	}
}

