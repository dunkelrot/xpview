package com.basf.xpview.pidviewer.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.basf.xpview.pidviewer.editors.PIDEditor;


public class ShowAllHandler extends AbstractHandler {


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (editor instanceof PIDEditor) {
			PIDEditor pidEditor = (PIDEditor) editor;
			pidEditor.getViewer().showAll();
		}
		
		return null;
	}
}
