package org.dexpi.xpview.pidviewer.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import org.dexpi.xpview.model.Thing;
import org.dexpi.xpview.model.graphics.RepresentationManager;
import org.dexpi.xpview.model.graphics.SoNode;
import org.dexpi.xpview.pidviewer.editors.PIDEditor;

public class ZoomTo extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (editor instanceof PIDEditor) {
			PIDEditor pidEditor = (PIDEditor) editor;
			ISelection selection = HandlerUtil.getCurrentSelection(event);
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection strutSel = (IStructuredSelection) selection;
				
				Object selObj = strutSel.getFirstElement();
				if (selObj != null && selObj instanceof Thing) {
					SoNode node = RepresentationManager.getInstance().getNode(selObj);
					if (node != null) {
						pidEditor.getViewer().zoomTo(node);
					}
				}
			}
			
		}
		
		return null;
	}
}
