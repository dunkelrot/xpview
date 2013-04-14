package com.basf.xpview.ui;

import org.eclipse.jface.text.ITextListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.console.TextConsoleViewer;

public class ConsoleTextViewer extends TextConsoleViewer implements ITextListener {

	public ConsoleTextViewer(Composite parent, TextConsole console) {
		super(parent, console);
		addTextListener(this);
	}

	@Override
	public void textChanged(org.eclipse.jface.text.TextEvent event) {
		revealEndOfDocument();
		
	}

}
