package org.dexpi.xpview.model;

/**
 * Base class for objects which annotate something. 
 * This class holds a {@link TextList} instance which collects all
 * annotation texts. 
 * 
 * @author Arndt Teinert
 *
 */
public class AnnotationItem extends Thing implements PropertyProvider, TextBag, Connectable {

	protected PropertyData propertyData;
	protected AnnotationContainer container;
	protected TextList textList;
	protected ConnectionPoints connections;
	
	public AnnotationItem(String name, AnnotationContainer container) {
		super(name);
		this.propertyData = new PropertyData(this);
		this.container = container;
		this.textList = new TextList(this);
		this.connections = new ConnectionPoints(this);
	}

	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
	public AnnotationContainer getContainer() {
		return container;
	}
	
	@Override
	public TextList getTextList() {
		return textList;
	}
	
	@Override
	public ConnectionPoints getConnectionPoints() {
		return connections;
	}
}
