package com.basf.xpview.model.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.basf.xpview.core.Event;
import com.basf.xpview.core.EventManager;
import com.basf.xpview.core.EventType;
import com.basf.xpview.model.EventTypes;
import com.basf.xpview.model.Plant;
import com.basf.xpview.model.io.xmplant.XMpLantImport;
import com.basf.xpview.utils.ExceptionDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
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
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		XMpLantImport importer = new XMpLantImport();
		try {
			FileDialog fOpen = new FileDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell());
			fOpen.setFilterExtensions(new String[] { "*.xml" }); //$NON-NLS-1$
			fOpen.setFilterNames(new String[] { "XMpLant" });

			String fileSelected = fOpen.open();
			if (fileSelected != null) {
				Plant plant = importer.read(new File(fileSelected));
				EventManager.getInstance().sendEvent(new Event(this, new EventType(EventTypes.FileImported), plant));
			}
		} catch (Exception ex) {
			ExceptionDialog.openException(ex);
		}
		return null;
	}
}
