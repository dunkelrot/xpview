package org.dexpi.xpview.ui;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.TextConsoleViewer;
import org.eclipse.ui.part.ViewPart;

public class MessageView extends ViewPart {

	public static final String ID = "org.dexpi.xpview.ui.MessageView"; //$NON-NLS-1$

	private static Logger log = Logger.getLogger(MessageView.class);

	protected MessageConsole console;
	protected TextConsoleViewer viewer;
	protected WriterAppender appender;

	@Override
	public void createPartControl(Composite parent) {

		this.console = new MessageConsole("MessageConsole", null);
		this.viewer = new ConsoleTextViewer(parent, console);

		WriterAppender appender = new WriterAppender(new PatternLayout("%d{HH:mm:ss,SSS} %-5p : %m%n"), console.newMessageStream()); //$NON-NLS-1$
		BasicConfigurator.configure(appender);
		Logger.getRootLogger().setLevel(Level.DEBUG);
		
		log.debug("Ready.");
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
