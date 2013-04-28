package org.dexpi.xpview.model;

import java.util.List;

/**
 * Classes which contain a list of {@link AnnotationItem}s should implement this interface. 
 * 
 * @author Arndt Teinert
 *
 */
public interface AnnotationContainer {

	/**
	 * Adds the given {@link AnnotationItem} to this container.
	 * It depends on the implementation how the {@link AnnotationItem} is stored.
	 *  
	 * @param AnnotationItem the item to add
	 */
	void addAnnotation(AnnotationItem annotationItem);
	
	List<? extends AnnotationItem> getAnnotations();
	
}

