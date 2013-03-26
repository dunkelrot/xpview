package com.basf.xpview.model.io;

import java.io.File;

import com.basf.xpview.model.Plant;

public abstract class Import {

	abstract public boolean canRead(File file);
	
	abstract public Plant read(File file) throws Exception;
}
