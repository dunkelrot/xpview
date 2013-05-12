package org.dexpi.xpview.model.prefs;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.dexpi.xpview.model.Activator;

public class PrefsPageImportIntergraph extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PrefsPageImportIntergraph() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(Activator.INTERGRAPH_APPLY_EQUIPMENT_POSITION, "Apply equipment position", getFieldEditorParent()));
		addField(new BooleanFieldEditor(Activator.INTERGRAPH_APPLY_LABEL_POSITION, "Apply label position", getFieldEditorParent()));
		addField(new BooleanFieldEditor(Activator.INTERGRAPH_APPLY_TEXT_POSITION, "Apply text position", getFieldEditorParent()));
		addField(new BooleanFieldEditor(Activator.INTERGRAPH_USE_CATALOG, "Reference catalog elements", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Intergraph specific tweaks");
	}

}
