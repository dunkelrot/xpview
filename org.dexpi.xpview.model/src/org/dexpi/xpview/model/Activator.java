package org.dexpi.xpview.model;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import org.dexpi.xpview.model.report.ReportConfigurations;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	public static final String IMPORT_MODE = "IMPORT_MODE"; //$NON-NLS-1$
	
	public static final String AVEVA_APPLY_EQUIPMENT_POSITION = "AVEVA_APPLY_EQUIPMENT_POSITION"; //$NON-NLS-1$
	public static final String AVEVA_APPLY_LABEL_POSITION = "AVEVA_APPLY_LABEL_POSITION"; //$NON-NLS-1$
	public static final String AVEVA_APPLY_TEXT_POSITION = "AVEVA_APPLY_TEXT_POSITION"; //$NON-NLS-1$
	public static final String AVEVA_USE_CATALOG = "AVEVA_USE_CATALOG";
	
	public static final String AUTODESK_APPLY_EQUIPMENT_POSITION = "AUTODESK_APPLY_EQUIPMENT_POSITION"; //$NON-NLS-1$
	public static final String AUTODESK_APPLY_LABEL_POSITION = "AUTODESK_APPLY_LABEL_POSITION"; //$NON-NLS-1$
	public static final String AUTODESK_APPLY_TEXT_POSITION = "AUTODESK_APPLY_TEXT_POSITION"; //$NON-NLS-1$
	public static final String AUTODESK_USE_CATALOG = "AUTODESK_USE_CATALOG";
	
	public static final String SIEMENS_APPLY_EQUIPMENT_POSITION = "SIEMENS_APPLY_EQUIPMENT_POSITION"; //$NON-NLS-1$
	public static final String SIEMENS_APPLY_LABEL_POSITION = "SIEMENS_APPLY_LABEL_POSITION"; //$NON-NLS-1$
	public static final String SIEMENS_APPLY_TEXT_POSITION = "SIEMENS_APPLY_TEXT_POSITION"; //$NON-NLS-1$
	public static final String SIEMENS_USE_CATALOG = "SIEMENS_USE_CATALOG"; //$NON-NLS-1$
	
	public static final String INTERGRAPH_APPLY_EQUIPMENT_POSITION = "INTERGRAPH_APPLY_EQUIPMENT_POSITION"; //$NON-NLS-1$
	public static final String INTERGRAPH_APPLY_LABEL_POSITION = "INTERGRAPH_APPLY_LABEL_POSITION"; //$NON-NLS-1$
	public static final String INTERGRAPH_APPLY_TEXT_POSITION = "INTERGRAPH_APPLY_TEXT_POSITION"; //$NON-NLS-1$
	public static final String INTERGRAPH_USE_CATALOG = "INTERGRAPH_USE_CATALOG"; //$NON-NLS-1$

	// The plug-in ID
	public static final String PLUGIN_ID = "org.dexpi.xpview.model"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		ReportConfigurations.getInstance().load();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		ReportConfigurations.getInstance().save();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
