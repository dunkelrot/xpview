package org.dexpi.xpview.model.prefs;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.dexpi.xpview.model.Activator;
import org.dexpi.xpview.model.io.ImportMode;

public class PrefsPageImport extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PrefsPageImport() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		
		String[][] values = {
				{"Strict",ImportMode.STRICT.toString()}, //$NON-NLS-1$
				{"System specific",ImportMode.SYSTEM_SPECIFIC.toString()}, //$NON-NLS-1$
				};
		
		addField(new ComboFieldEditor(Activator.IMPORT_MODE, "Import mode", values, getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Settings for the imports");
	}

}
