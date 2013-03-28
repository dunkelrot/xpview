package com.basf.xpview.model;

import java.util.List;

public interface Container<T extends Thing> {

	List<T> getChildren();
	
	T getParent();
	
	void add(T element);
	
}
