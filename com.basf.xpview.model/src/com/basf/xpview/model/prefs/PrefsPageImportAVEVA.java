package com.basf.xpview.model.prefs;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.basf.xpview.model.Activator;

public class PrefsPageImportAVEVA extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PrefsPageImportAVEVA() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(Activator.AVEVA_APPLY_EQUIPMENT_POSITION, "Apply equipment position", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("AVEVA specific tweaks");
	}

}
