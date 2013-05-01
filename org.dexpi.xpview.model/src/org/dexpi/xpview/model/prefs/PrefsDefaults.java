package org.dexpi.xpview.model.prefs;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.dexpi.xpview.model.Activator;
import org.dexpi.xpview.model.io.ImportMode;

public class PrefsDefaults extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore prefs = Activator.getDefault().getPreferenceStore();
		
		prefs.setDefault(Activator.IMPORT_MODE, ImportMode.STRICT.toString());
		
		prefs.setDefault(Activator.AVEVA_APPLY_EQUIPMENT_POSITION, false);
		prefs.setDefault(Activator.AVEVA_APPLY_LABEL_POSITION, false);
		prefs.setDefault(Activator.AVEVA_APPLY_TEXT_POSITION, true);
		prefs.setDefault(Activator.AVEVA_USE_CATALOG, false);
		
		prefs.setDefault(Activator.AUTODESK_APPLY_EQUIPMENT_POSITION, true);
		prefs.setDefault(Activator.AUTODESK_APPLY_LABEL_POSITION, false);
		prefs.setDefault(Activator.AUTODESK_APPLY_TEXT_POSITION, true);
		prefs.setDefault(Activator.AUTODESK_USE_CATALOG, true);
		
		prefs.setDefault(Activator.SIEMENS_APPLY_EQUIPMENT_POSITION, false);
		prefs.setDefault(Activator.SIEMENS_APPLY_LABEL_POSITION, true);
		prefs.setDefault(Activator.SIEMENS_APPLY_TEXT_POSITION, false);
		prefs.setDefault(Activator.SIEMENS_USE_CATALOG, false);
	}

}
