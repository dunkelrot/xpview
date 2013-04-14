package com.basf.xpview.utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ExceptionDialogCtrl extends Composite {

	private Label label = null;
	private Text stackTrace = null;
	private Text errorMessage = null;
	private Label label1 = null;
	private Button export = null;
	
	public ExceptionDialogCtrl(Composite parent, int style) {
		super(parent, style);
		initialize();

		stackTrace.setFont(new Font(parent.getDisplay(), "Courier", 10, SWT.NORMAL));
	}

	private void initialize() {
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.END;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData21 = new GridData();
		gridData21.heightHint = 80;
		gridData21.horizontalSpan = 2;
		gridData21.grabExcessHorizontalSpace = true;
		gridData21.horizontalAlignment = GridData.FILL;
		gridData21.verticalAlignment = GridData.CENTER;
		gridData21.grabExcessVerticalSpace = false;
		gridData21.widthHint = 500;

		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.BEGINNING;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.widthHint = 500;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.FILL;
		gridData1.horizontalSpan = 2;
		gridData1.grabExcessVerticalSpace = true;
		gridData1.heightHint = 120;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		label = new Label(this, SWT.NONE);
		label.setText("Error message:");
		label.setLayoutData(gridData2);
		export = new Button(this, SWT.NONE);
		export.setText("Export to file");
		errorMessage = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		errorMessage.setLayoutData(gridData21);
		label1 = new Label(this, SWT.NONE);
		label1.setText("Stacktrace:");
		stackTrace = new Text(this, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		export.setLayoutData(gridData3);
		stackTrace.setLayoutData(gridData1);
		this.setLayout(gridLayout);
		this.setSize(new Point(516, 279));
		
		export.addSelectionListener(new SelectionListener() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        
		        FileDialog fOpen = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
                fOpen.setFilterExtensions(new String[] {"*.txt"} );
                fOpen.setFilterNames(new String[] {"TXT files"} );
                String fileSelected = fOpen.open();
                if (fileSelected != null) {
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter(new File(fileSelected)));
                        
                        writer.println("Java version: " + System.getProperty("java.version"));
                        writer.println("OS name:      " + System.getProperty("os.name"));
                        writer.println("OS arch:      " + System.getProperty("os.arch"));
                        writer.println("OS version:   " + System.getProperty("os.version"));
                        writer.println("User:         " + System.getProperty("user.name"));
                        writer.println("");

                        writer.println("Message");
                        writer.println("-----------------------------------------------------");
                        writer.println(errorMessage.getText());
                        writer.println("");
                        writer.println("StackTrace");
                        writer.println("-----------------------------------------------------");
                        writer.println(stackTrace.getText());
                        writer.close();
                    } catch (IOException ex) {
                        MessageDialog
                        .openError(Display.getCurrent().getActiveShell(), "Error", ex.getMessage());
                    }
                }
		    }
		    
		    @Override
		    public void widgetDefaultSelected(SelectionEvent e) {
		    }
		});
	}
	
	public void setThrowable(Throwable throwable) {
		if (throwable.getMessage() != null) {
			setErrorMessage(throwable.getMessage());
		} else {
			setErrorMessage("No message available.");
		}
		
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		
		throwable.printStackTrace(printWriter);
		
		setStackTrace(writer.toString());
	}
	
	public void setErrorMessage(String msg) {
		errorMessage.setText(msg);
	}

	public void setStackTrace(String value) {
		stackTrace.setText(value);
	}
	
}  //  @jve:decl-index=0:visual-constraint="0,0"

