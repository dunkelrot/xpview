package com.basf.xpview.model;

/**
 * A Drawing represents one piece of digital paper. 
 * 
 * @author Arndt Teinert
 *
 */
public class Drawing extends Thing implements PropertyItem {
	
	protected PropertyData propertyData;
	protected DrawingList drawingList;

	/**
	 * Constructor, nothing special about this.
	 * @param drawingList
	 * @param name
	 */
	public Drawing(DrawingList drawingList, String name) {
		super(name);
		this.drawingList = drawingList;
		this.propertyData = new PropertyData(this);
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
	
}
