package org.dexpi.xpview.model.prefs;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.dexpi.xpview.model.Activator;

public class PrefsPageImportAVEVA extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PrefsPageImportAVEVA() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(Activator.AVEVA_APPLY_EQUIPMENT_POSITION, "Apply equipment position", getFieldEditorParent()));
		addField(new BooleanFieldEditor(Activator.AVEVA_APPLY_LABEL_POSITION, "Apply label position", getFieldEditorParent()));
		addField(new BooleanFieldEditor(Activator.AVEVA_APPLY_TEXT_POSITION, "Apply text position", getFieldEditorParent()));
		addField(new BooleanFieldEditor(Activator.AVEVA_USE_CATALOG, "Reference catalog elements", getFieldEditorParent()));
		
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("AVEVA specific tweaks");
	}

}
