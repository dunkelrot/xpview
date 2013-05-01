package org.dexpi.xpview.model;

public class Text extends Thing implements PropertyProvider {

	protected PropertyData propertyData;
	
	protected String value;
	protected String set;
	protected String dependanteAttribute;
	protected TextList textList;
	
	protected String itemReferenceID;
	
	public Text(TextList textList) {
		super(Text.class.getSimpleName());
		this.propertyData = new PropertyData(this);
		this.textList = textList;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getItemReferenceID() {
		return itemReferenceID;
	}

	public void setItemReferenceID(String itemReferenceID) {
		this.itemReferenceID = itemReferenceID;
	}

	public String getDependanteAttribute() {
		return dependanteAttribute;
	}

	public void setDependanteAttribute(String dependanteAttribute) {
		this.dependanteAttribute = dependanteAttribute;
	}
	
	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
	public TextList getTextList() {
		return textList;
	}
}
