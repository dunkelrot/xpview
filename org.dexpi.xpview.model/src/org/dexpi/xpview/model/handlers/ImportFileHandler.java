package org.dexpi.xpview.model.handlers;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import org.dexpi.xpview.core.Event;
import org.dexpi.xpview.core.EventManager;
import org.dexpi.xpview.model.Plant;
import org.dexpi.xpview.model.events.EventTypes;
import org.dexpi.xpview.model.graphics.RepresentationManager;
import org.dexpi.xpview.model.io.XMpLantImport;
import org.dexpi.xpview.utils.ExceptionDialog;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ImportFileHandler extends AbstractHandler {
	
	/**
	 * The constructor.
	 */
	public ImportFileHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().closeAllEditors(false);
		
		try {
			FileDialog fOpen = new FileDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell());
			fOpen.setFilterExtensions(new String[] { "*.xml" }); //$NON-NLS-1$
			fOpen.setFilterNames(new String[] { "XMpLant" });

			final String fileSelected = fOpen.open();
			if (fileSelected != null) {
				ProgressMonitorDialog dlg = new ProgressMonitorDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell());
				dlg.run(true, false, new IRunnableWithProgress() {
					
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						monitor.beginTask("Loading file...", IProgressMonitor.UNKNOWN);
						XMpLantImport importer = new XMpLantImport();
						try {
							RepresentationManager.getInstance().clear();
							Plant plant = importer.read(new File(fileSelected), RepresentationManager.getInstance(), monitor);
							EventManager.getInstance().sendEvent(new Event(this, EventTypes.FileImported, plant));
						} catch (Exception ex) {
							ExceptionDialog.openException(ex);
						}
						monitor.done();
					}
				});
			}
			
		} catch (Exception ex) {
			ExceptionDialog.openException(ex);
		}
		return null;
	}
}
