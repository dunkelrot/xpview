package com.basf.xpview.model.prefs;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.basf.xpview.model.Activator;

public class PrefsPageImportAutoDesk extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PrefsPageImportAutoDesk() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(Activator.AUTODESK_APPLY_EQUIPMENT_POSITION, "Apply equipment position", getFieldEditorParent()));
		addField(new BooleanFieldEditor(Activator.AUTODESK_USE_CATALOG, "Reference catalog elements", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("AutoDesk specific tweaks");
	}

}
