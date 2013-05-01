package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Drawing represents one piece of digital paper. 
 * 
 * @author Arndt Teinert
 *
 */
public class Drawing extends Thing implements PropertyProvider, AnnotationContainer, TextBag {
	
	protected PropertyData propertyData;
	protected DrawingList drawingList;
	protected DrawingSizeType size;
	protected DrawingBorder border;
	protected TextList textList;
	protected ArrayList<AnnotationItem> annotations;
	
	/**
	 * Constructor, nothing special about this.
	 * The paper size is set to A1 by default.
	 * 
	 * @param drawingList the owning {@link DrawingList}
	 * @param name the name of this {@link Drawing}
	 */
	public Drawing(DrawingList drawingList, String name) {
		super(name);
		this.drawingList = drawingList;
		this.propertyData = new PropertyData(this);
		this.size = DrawingSizeType.A1;
		this.border = new DrawingBorder(this);
		this.textList = new TextList(this);
		this.annotations = new ArrayList<AnnotationItem>();
	}
	
	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
	/**
	 * Returns the owning {@link DrawingList} object
	 * @return the owning {@link DrawingList}
	 */
	public DrawingList getDrawingList() {
		return drawingList;
	}
	
	/**
	 * Sets the paper size of this {@link Drawing}
	 * @param size the size
	 */
	public void setSize(DrawingSizeType size) {
		this.size = size;
	}
	
	/**
	 * Returns the paper size
	 * @return the paper size
	 */
	public DrawingSizeType getSize() {
		return size;
	}
	
	public DrawingBorder getBorder() {
		return border;
	}
	
	@Override
	public TextList getTextList() {
		return textList;
	}

	@Override
	public List<? extends AnnotationItem> getAnnotations() {
		return annotations;
	}

	@Override
	public void addAnnotation(AnnotationItem annotationItem) {
		annotations.add(annotationItem);
	}
}
