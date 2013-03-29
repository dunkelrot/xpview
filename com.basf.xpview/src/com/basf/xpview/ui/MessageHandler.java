package com.basf.xpview.ui;

import java.io.OutputStream;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class MessageHandler extends StreamHandler {

	public MessageHandler(OutputStream out) {
		super(out, new MessageFormatter());
	}
	
	@Override
	public synchronized void publish(LogRecord arg0) {
		super.publish(arg0);
		flush();
	}
}
