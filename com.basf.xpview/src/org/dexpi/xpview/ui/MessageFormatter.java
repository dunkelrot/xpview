package com.basf.xpview.ui;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MessageFormatter extends Formatter {

	protected SimpleDateFormat format;
	protected StringBuffer buffer;
	
	public MessageFormatter() {
		this.format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"); //$NON-NLS-1$
		this.buffer = new StringBuffer();
	}
	
	
	@Override
	public String format(LogRecord record) {
		buffer.setLength(0);
		buffer.append(format.format(record.getMillis()));
		buffer.append(" "); //$NON-NLS-1$
		buffer.append(record.getLevel().getName());
		buffer.append(": "); //$NON-NLS-1$
		buffer.append(record.getMessage());
		buffer.append("\n"); //$NON-NLS-1$
		return buffer.toString();
	}
	
}
